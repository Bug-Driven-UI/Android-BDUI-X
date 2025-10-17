package ru.bugdrivenui.bduix.presentation.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.bugdrivenui.bduix.R
import ru.bugdrivenui.bduix.core.navigation.NavigationManager
import ru.bugdrivenui.bduix.core.navigation.NavigationRoute
import ru.bugdrivenui.bduix.core.resources.IResourcesWrapper
import ru.bugdrivenui.bduix.domain.repository.ILoginRepository
import ru.bugdrivenui.bduix.domain.state.Result
import ru.bugdrivenui.bduix.presentation.utils.PresentationConstants.START_SCREEN_NAME
import ru.bugdrivenui.bduix.utils.emptyString
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: ILoginRepository,
    private val navigationManager: NavigationManager,
    private val resourcesWrapper: IResourcesWrapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnApiKeyChanged -> onApiKeyChanged(event.apiKey)
            LoginEvent.OnLoginClicked -> onLoginClicked()
        }
    }

    private fun onApiKeyChanged(value: String) {
        _uiState.update { state ->
            state.copy(
                apiKey = value,
                errorText = null,
            )
        }
    }

    private fun onLoginClicked() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        val result = loginRepository.isValidApiKey(uiState.value.apiKey)
        when (result) {
            is Result.Error -> {
                // ignore auth
                loadInitialBduiScreen()
            }
            is Result.Success -> {
                if (result.data) {
                    loadInitialBduiScreen()
                    _uiState.update { it.copy(isLoading = false) }
                } else {
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            errorText = resourcesWrapper.getString(R.string.api_key_error),
                        )
                    }
                }
            }
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

data class LoginUiState(
    val apiKey: String = emptyString(),
    val isLoading: Boolean = false,
    val errorText: String? = null,
)

sealed interface LoginEvent {
    data class OnApiKeyChanged(val apiKey: String) : LoginEvent
    data object OnLoginClicked : LoginEvent
}
