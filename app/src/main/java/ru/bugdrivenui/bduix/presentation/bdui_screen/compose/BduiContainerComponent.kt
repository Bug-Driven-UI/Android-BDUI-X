package ru.bugdrivenui.bduix.presentation.bdui_screen.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toCompose
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentSize
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.utils.bduiBaseProperties
import ru.bugdrivenui.bduix.presentation.utils.ifNotNull

@Composable
fun BduiContainerComponent(
    modifier: Modifier,
    component: BduiComponentUi.Container,
    onAction: (BduiActionUi) -> Unit,
) {
    when (component) {
        is BduiComponentUi.Box -> {
            Box(
                modifier = modifier,
                contentAlignment = component.contentAlignment.toCompose(),
            ) {
                component.children.forEach { child ->
                    BduiComponentWrapper(
                        component = child,
                        onAction = onAction,
                    )
                }
            }
        }

        is BduiComponentUi.Column -> {
            Column(
                modifier = modifier,
                verticalArrangement = component.verticalArrangement.toCompose(),
                horizontalAlignment = component.horizontalAlignment.toCompose(),
            ) {
                component.children.forEach { child ->
                    BduiComponentWrapper(
                        component = child,
                        onAction = onAction,
                    )
                }
            }
        }

        is BduiComponentUi.Row -> {
            val arrangement = component.horizontalArrangement.toCompose()
            val alignment = component.verticalAlignment.toCompose()

            if (component.isScrollable) {
                LazyRow(
                    modifier = modifier,
                    horizontalArrangement = arrangement,
                    verticalAlignment = alignment,
                ) {
                    items(component.children) { child ->
                        BduiComponentWrapper(
                            component = child,
                            onAction = onAction,
                        )
                    }
                }
            } else {
                Row(
                    modifier = modifier,
                    horizontalArrangement = arrangement,
                    verticalAlignment = alignment,
                ) {
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

@Composable
private fun LazyItemScope.BduiComponentWrapper(
    component: BduiComponentUi,
    onAction: (BduiActionUi) -> Unit,
) {
    // ignore weight
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
