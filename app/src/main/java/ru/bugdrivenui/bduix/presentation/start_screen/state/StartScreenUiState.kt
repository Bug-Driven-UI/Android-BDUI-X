package ru.bugdrivenui.bduix.presentation.start_screen.state

sealed interface StartScreenUiState {

    data object Loading : StartScreenUiState

    data object Error : StartScreenUiState

    data object Success : StartScreenUiState
}