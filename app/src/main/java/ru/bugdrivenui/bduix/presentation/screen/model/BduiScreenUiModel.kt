package ru.bugdrivenui.bduix.presentation.screen.model

import androidx.compose.runtime.Immutable

@Immutable
data class BduiScreenUiModel(
    val screenId: String,
    val rootComponentsIds: List<String>,
    val componentById: Map<String, BduiComponentUi>,
    val childrenIdsById: Map<String, List<String>>,
    val stateById: Map<String, BduiComponentState>,
)

@Immutable
sealed interface BduiComponentState {

    data object Stateless : BduiComponentState

    data class Text(
        val text: String,
    ) : BduiComponentState

    data class Button(
        val text: String,
        val isEnabled: Boolean,
    ) : BduiComponentState

    data class Input(
        val text: String,
        val isEnabled: Boolean,
    ) : BduiComponentState
}
