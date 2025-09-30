package ru.bugdrivenui.bduix.presentation.common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data object Error : UiState<Nothing>
    data class Content<T>(val data: T) : UiState<T>
}

fun <T> MutableStateFlow<UiState<T>>.updateIfContent(updateBlock: (T) -> T) {
    update { state ->
        if (state is UiState.Content) UiState.Content(updateBlock(state.data)) else state
    }
}
