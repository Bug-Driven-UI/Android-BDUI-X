package ru.bugdrivenui.bduix.presentation.bdui_screen.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
            .bduiBaseProperties(component)
            .ifNotNull(component.interactions?.onClick) { onClickActions ->
                clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    enabled = (component as? BduiComponentUi.Button)?.enabled ?: true,
                    onClick = { onClickActions.forEach { onAction(it) } },
                )
            },
        component = component,
        onAction = onAction,
    )
}

@Composable
private fun ColumnScope.BduiComponentWrapper(
    component: BduiComponentUi,
    onAction: (BduiActionUi) -> Unit,
) {
    val weightedHeight = (component.height as? BduiComponentSize.Weighted)?.fraction

    BduiComponent(
        modifier = Modifier
            .bduiBaseProperties(component)
            .ifNotNull(weightedHeight) { weight(it) }
            .ifNotNull(component.interactions?.onClick) { onClickActions ->
                clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    enabled = (component as? BduiComponentUi.Button)?.enabled ?: true,
                    onClick = { onClickActions.forEach { onAction(it) } },
                )
            },
        component = component,
        onAction = onAction,
    )
}

@Composable
private fun RowScope.BduiComponentWrapper(
    component: BduiComponentUi,
    onAction: (BduiActionUi) -> Unit,
) {
    val weightedWidth = (component.width as? BduiComponentSize.Weighted)?.fraction

    BduiComponent(
        modifier = Modifier
            .bduiBaseProperties(component)
            .ifNotNull(weightedWidth) { weight(it) }
            .ifNotNull(component.interactions?.onClick) { onClickActions ->
                clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    enabled = (component as? BduiComponentUi.Button)?.enabled ?: true,
                    onClick = { onClickActions.forEach { onAction(it) } },
                )
            },
        component = component,
        onAction = onAction,
    )
}