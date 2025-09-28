package ru.bugdrivenui.bduix.presentation.bdui_screen.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentSize
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.utils.bduiBaseProperties
import ru.bugdrivenui.bduix.utils.ifNotNull

@Composable
fun BduiContainerComponent(
    modifier: Modifier,
    component: BduiComponentUi.Container,
    onAction: (BduiActionUi) -> Unit,
) {
    when (component) {
        is BduiComponentUi.Box -> {
            Box(modifier = modifier) {
                component.children.forEach { child ->
                    BduiComponentWrapper(
                        component = child,
                        onAction = onAction,
                    )
                }
            }
        }

        is BduiComponentUi.Column -> {
            Column(modifier = modifier) {
                component.children.forEach { child ->
                    BduiComponentWrapper(
                        component = child,
                        onAction = onAction,
                    )
                }
            }
        }

        is BduiComponentUi.Row -> {
            Row(modifier = modifier) {
                component.children.forEach { child ->
                    BduiComponentWrapper(
                        component = child,
                        onAction = onAction,
                    )
                }
            }
        }
    }
}

@Composable
private fun BoxScope.BduiComponentWrapper(
    component: BduiComponentUi,
    onAction: (BduiActionUi) -> Unit,
) {
    BduiComponent(
        modifier = Modifier
            .bduiBaseProperties(
                component = component.baseProperties,
                onAction = onAction,
                buttonEnabled = (component as? BduiComponentUi.Button)?.enabled
            ),
        component = component,
        onAction = onAction,
    )
}

@Composable
private fun ColumnScope.BduiComponentWrapper(
    component: BduiComponentUi,
    onAction: (BduiActionUi) -> Unit,
) {
    val weightedHeight = (component.baseProperties.height as? BduiComponentSize.Weighted)?.fraction

    BduiComponent(
        modifier = Modifier
            .bduiBaseProperties(
                component = component.baseProperties,
                onAction = onAction,
                buttonEnabled = (component as? BduiComponentUi.Button)?.enabled
            )
            .ifNotNull(weightedHeight) { weight(it) },
        component = component,
        onAction = onAction,
    )
}

@Composable
private fun RowScope.BduiComponentWrapper(
    component: BduiComponentUi,
    onAction: (BduiActionUi) -> Unit,
) {
    val weightedWidth = (component.baseProperties.width as? BduiComponentSize.Weighted)?.fraction

    BduiComponent(
        modifier = Modifier
            .bduiBaseProperties(
                component = component.baseProperties,
                onAction = onAction,
                buttonEnabled = (component as? BduiComponentUi.Button)?.enabled
            )
            .ifNotNull(weightedWidth) { weight(it) },
        component = component,
        onAction = onAction,
    )
}