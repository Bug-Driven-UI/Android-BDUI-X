package ru.bugdrivenui.bduix.presentation.screen.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.bugdrivenui.bduix.presentation.screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.screen.model.BduiComponentUi

@Composable
fun BduiComponent(
    modifier: Modifier,
    component: BduiComponentUi,
    onAction: (BduiActionUi) -> Unit,
) {
    when (component) {
        is BduiComponentUi.Container -> {
            BduiContainerComponent(
                modifier = modifier,
                component = component,
                onAction = onAction,
            )
        }

        is BduiComponentUi.Button -> {
            BduiButtonComponent(
                modifier = modifier,
                component = component,
            )
        }

        is BduiComponentUi.Image -> TODO()
        is BduiComponentUi.Input -> TODO()
        is BduiComponentUi.Text -> {
            BduiTextComponent(
                modifier = modifier,
                component = component,
            )
        }
    }
}
