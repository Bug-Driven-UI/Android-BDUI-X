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
import ru.bugdrivenui.bduix.data.repository.LoginRepository
import ru.bugdrivenui.bduix.presentation.start_screen.state.StartScreenUiState
import ru.bugdrivenui.bduix.presentation.utils.PresentationConstants.START_SCREEN_NAME
import javax.inject.Inject

private const val LOCAL_AUTH_TOGGLE = false

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val loginRepository: LoginRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<StartScreenUiState>(StartScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        resolveFirstScreen()
    }

    private fun resolveFirstScreen() = viewModelScope.launch {
        delay(5000)
        if (!isLocalAuthEnabled() || loginRepository.apiKeyExists()) {
            navigateToInitialBduiScreen()
        } else {
            navigateToLoginScreen()
        }
    }

    private fun isLocalAuthEnabled(): Boolean = LOCAL_AUTH_TOGGLE

    private fun navigateToInitialBduiScreen() {
        navigationManager.replace(
            route = NavigationRoute.BduiScreen(
                args = NavigationRoute.BduiScreen.Args(
                    screenName = START_SCREEN_NAME,
                    screenParams = null,
                )
            ),
        )
    }

    private fun navigateToLoginScreen() {
        navigationManager.replace(
            route = NavigationRoute.LoginScreen
        )
    }
}