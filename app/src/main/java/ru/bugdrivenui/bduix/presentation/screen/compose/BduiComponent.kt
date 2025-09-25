package ru.bugdrivenui.bduix.presentation.screen.compose

import androidx.compose.runtime.Composable
import ru.bugdrivenui.bduix.presentation.screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.screen.model.BduiComponentUi

@Composable
fun BduiComponent(
    component: BduiComponentUi,
    onAction: (BduiActionUi) -> Unit,
) {
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