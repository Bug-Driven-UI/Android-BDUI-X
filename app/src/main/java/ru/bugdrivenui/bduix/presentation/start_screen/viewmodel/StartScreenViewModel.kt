package ru.bugdrivenui.bduix.presentation.start_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.bugdrivenui.bduix.core.navigation.NavigationManager
import ru.bugdrivenui.bduix.core.navigation.NavigationRoute
import ru.bugdrivenui.bduix.presentation.start_screen.state.StartScreenUiState
import javax.inject.Inject

private const val START_SCREEN_NAME = "startScreen"

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow<StartScreenUiState>(StartScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            loadInitialBduiScreen()
        }
    }

    private fun loadInitialBduiScreen() {
        navigationManager.replace(
            route = NavigationRoute.BduiScreen(
                args = NavigationRoute.BduiScreen.Args(
                    screenName = START_SCREEN_NAME,
                    screenParams = null,
                )
            ),
        )
    }
}