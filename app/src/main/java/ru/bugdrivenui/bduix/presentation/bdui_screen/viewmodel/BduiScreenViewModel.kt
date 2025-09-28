package ru.bugdrivenui.bduix.presentation.bdui_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonElement
import ru.bugdrivenui.bduix.data.model.ScreenRenderRequestModel
import ru.bugdrivenui.bduix.domain.interactor.BduiInteractor
import ru.bugdrivenui.bduix.domain.state.State
import ru.bugdrivenui.bduix.navigation.NavigationManager
import ru.bugdrivenui.bduix.presentation.bdui_screen.factory.BduiScreenFactory
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.RenderedScreenUi
import ru.bugdrivenui.bduix.presentation.common.UiState
import javax.inject.Inject

@HiltViewModel
class BduiScreenViewModel @Inject constructor(
    private val bduiInteractor: BduiInteractor,
    private val screenFactory: BduiScreenFactory,
    private val navigationManager: NavigationManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<RenderedScreenUi>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        startCollectFlow()
    }

    fun onAction(action: BduiActionUi) {
        when (action) {
            is BduiActionUi.Command -> onCommand(
                name = action.name,
                params = action.params,
            )
            is BduiActionUi.UpdateScreen -> onUpdateScreen(
                screenName = action.screenName,
                screenNavigationParams = action.screenNavigationParams
            )

            BduiActionUi.NavigateBack -> onNavigateBack()
        }
    }

    private fun startCollectFlow() = viewModelScope.launch {
        bduiInteractor.getScreen(
            request = ScreenRenderRequestModel(
                data = ScreenRenderRequestModel.Data(
                    screenName = "startScreen",
                    variables = null,
                ),
            ),
        )
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
                                data = screenFactory.create(
                                    screen = state.data.screen,
                                ),
                            )
                        }
                    }
                }
            }
    }

    private fun onCommand(
        name: String,
        params: Map<String, JsonElement>?,
    ) {
        // TODO
    }

    private fun onUpdateScreen(
        screenName: String,
        screenNavigationParams: Map<String, JsonElement>?,
    ) {
        // TODO
    }

    private fun onNavigateBack() {
        navigationManager.back()
    }
}