package ru.bugdrivenui.bduix.presentation.bdui_screen.model

import androidx.compose.runtime.Immutable

@Immutable
data class RenderedScreenUi(
    val screenName: String,
    val version: Int,
    val components: List<BduiComponentUi>,
    val scaffold: BduiScaffoldUi?,
)