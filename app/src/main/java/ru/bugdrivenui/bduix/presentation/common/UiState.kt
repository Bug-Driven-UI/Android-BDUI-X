package ru.bugdrivenui.bduix.presentation.common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

sealed interface UiState<out T> {

    val key: Key

    data object Loading : UiState<Nothing> {
        override val key = Key.LOADING
    }

    data object Error : UiState<Nothing> {
        override val key = Key.ERROR
    }

    data class Content<T>(val data: T) : UiState<T> {
        override val key = Key.CONTENT
    }

    enum class Key {
        LOADING,
        ERROR,
        CONTENT,
    }
}

fun <T> MutableStateFlow<UiState<T>>.updateIfContent(updateBlock: (T) -> T) {
    update { state ->
        if (state is UiState.Content) UiState.Content(updateBlock(state.data)) else state
    }
}
