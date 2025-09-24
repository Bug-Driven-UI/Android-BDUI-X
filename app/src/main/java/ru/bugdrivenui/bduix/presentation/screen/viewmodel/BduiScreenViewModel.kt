package ru.bugdrivenui.bduix.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.bugdrivenui.bduix.domain.interactor.BduiInteractor
import ru.bugdrivenui.bduix.domain.state.State
import ru.bugdrivenui.bduix.presentation.common.UiState
import ru.bugdrivenui.bduix.presentation.screen.factory.BduiScreenFactory
import ru.bugdrivenui.bduix.presentation.screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.screen.model.BduiComponentState
import ru.bugdrivenui.bduix.presentation.screen.model.BduiScreenUiModel
import javax.inject.Inject

@HiltViewModel
class BduiScreenViewModel @Inject constructor(
    private val bduiInteractor: BduiInteractor,
    private val screenFactory: BduiScreenFactory,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<BduiScreenUiModel>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        startCollectFlow()
    }

    fun stateFor(componentId: String): StateFlow<BduiComponentState> {
        return uiState
            .map { state ->
                when (state) {
                    is UiState.Content -> {
                        state.data.stateById[componentId] ?: BduiComponentState.Stateless
                    }
                    else -> BduiComponentState.Stateless
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = BduiComponentState.Stateless,
            )
    }

    fun onAction(action: BduiActionUi) {
        when (action) {
            is BduiActionUi.Command -> onCommand(action.name, action.params)
            is BduiActionUi.UpdateScreen -> onUpdateScreen(action.screenName, action.screenNavigationParams)
        }
    }

    private fun startCollectFlow() = viewModelScope.launch {
        bduiInteractor.getScreen()
            .collectLatest { state ->
                when (state) {
                    State.Loading -> {
                        _uiState.update { UiState.Loading }
                    }
                    is State.Error -> {
                        _uiState.update { UiState.Error }
                    }
                    is State.Success -> {
                        _uiState.update {
                            UiState.Content(
                                data = screenFactory.create(state.data),
                            )
                        }
                    }
                }
            }
    }

    private fun onCommand(
        name: String,
        params: Map<String, String>?,
    ) {
        // TODO
    }

    private fun onUpdateScreen(
        screenName: String,
        screenNavigationParams: Map<String, String>?,
    ) {
        // TODO
    }
}