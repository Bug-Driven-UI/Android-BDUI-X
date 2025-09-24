package ru.bugdrivenui.bduix.presentation.common

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data object Error : UiState<Nothing>
    data class Content<T>(val data: T) : UiState<T>
}