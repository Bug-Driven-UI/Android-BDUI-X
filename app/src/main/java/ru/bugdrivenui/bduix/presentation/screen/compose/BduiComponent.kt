package ru.bugdrivenui.bduix.presentation.screen.compose

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.StateFlow
import ru.bugdrivenui.bduix.presentation.screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.screen.model.BduiComponentState
import ru.bugdrivenui.bduix.presentation.screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.screen.model.BduiScreenUiModel

@Composable
fun BduiComponent(
    componentId: String,
    model: BduiScreenUiModel,
    onAction: (BduiActionUi) -> Unit,
    componentStateProvider: (String) -> StateFlow<BduiComponentState>,
) {
    val component = model.componentById[componentId] ?: return
    // TODO для локального состояния
    // val state = componentStateProvider(componentId).collectAsStateWithLifecycle()

    when (component) {
        is BduiComponentUi.Box -> TODO()
        is BduiComponentUi.Button -> TODO()
        is BduiComponentUi.Column -> TODO()
        is BduiComponentUi.Image -> TODO()
        is BduiComponentUi.Input -> TODO()
        is BduiComponentUi.Row -> TODO()
        is BduiComponentUi.Text -> TODO()
    }
}