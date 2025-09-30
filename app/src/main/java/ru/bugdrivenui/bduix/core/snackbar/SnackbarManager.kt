package ru.bugdrivenui.bduix.core.snackbar

import androidx.compose.material3.SnackbarDuration
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SnackbarManager @Inject constructor() {

    private val _messages = MutableSharedFlow<SnackbarMessage>(extraBufferCapacity = 1)
    val messages = _messages.asSharedFlow()

    fun show(
        text: String,
        duration: SnackbarDuration = SnackbarDuration.Short,
    ) {
        _messages.tryEmit(SnackbarMessage(text, duration))
    }
}