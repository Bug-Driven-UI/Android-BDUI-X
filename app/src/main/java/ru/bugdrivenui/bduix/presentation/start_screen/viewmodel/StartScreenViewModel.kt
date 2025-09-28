package ru.bugdrivenui.bduix.presentation.start_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import ru.bugdrivenui.bduix.navigation.NavigationManager
import ru.bugdrivenui.bduix.navigation.NavigationRoute
import ru.bugdrivenui.bduix.presentation.start_screen.state.StartScreenUiState
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow<StartScreenUiState>(StartScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(10000)
            loadInitialBduiScreen()
        }
    }

    private fun loadInitialBduiScreen() {
        // TODO как будем загружать начальный экран? запрос на параметры по id пользователя?
        navigationManager.replace(
            route = NavigationRoute.BduiScreen(
                args = NavigationRoute.BduiScreen.Args(
                    screenId = "some_screen_id",
                    screenParams = mapOf("key" to buildJsonObject {}),
                )
            ),
        )
    }
}