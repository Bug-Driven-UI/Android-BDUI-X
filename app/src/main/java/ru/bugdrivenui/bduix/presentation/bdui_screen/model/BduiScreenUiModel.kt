package ru.bugdrivenui.bduix.presentation.bdui_screen.model

import androidx.compose.runtime.Immutable

@Immutable
data class BduiScreenUiModel(
    val screenId: String,
    val components: List<BduiComponentUi>,
)