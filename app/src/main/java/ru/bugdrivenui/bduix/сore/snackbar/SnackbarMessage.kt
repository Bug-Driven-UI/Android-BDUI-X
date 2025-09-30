package ru.bugdrivenui.bduix.сore.snackbar

import androidx.compose.material3.SnackbarDuration

data class SnackbarMessage(
    val text: String,
    val duration: SnackbarDuration = SnackbarDuration.Short,
)