package ru.bugdrivenui.bduix.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toComposeColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentInsetsUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentSize
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiShape

inline fun <T : Any> Modifier.ifNotNull(
    value: T?,
    block: Modifier.(T) -> Modifier
): Modifier =
    if (value != null) block(this, value) else this

@Composable
fun Modifier.bduiBaseProperties(
    component: BduiComponentUi.BaseProperties,
    onAction: (BduiActionUi) -> Unit,
    buttonEnabled: Boolean?,
): Modifier {
    val shape = when (component.shape) {
        is BduiShape.RoundedCorners -> RoundedCornerShape(
            topStart = component.shape.topStart.dp,
            topEnd = component.shape.topEnd.dp,
            bottomStart = component.shape.bottomStart.dp,
            bottomEnd = component.shape.bottomEnd.dp,
        )
        null -> RectangleShape
    }

    return this.then(
        Modifier
            .bduiPadding(component.margins)
            .bduiSize(
                width = component.width,
                height = component.height,
            )
            .clip(shape)
            .ifNotNull(component.backgroundColor) { color ->
                background(
                    color = color.toComposeColor(),
                    shape = shape,
                )
            }
            .ifNotNull(component.border) { border ->
                border(
                    width = border.thickness.dp,
                    color = border.color.toComposeColor(),
                    shape = shape,
                )
            }
            .ifNotNull(component.interactions?.onClick) { onClickActions ->
                clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    enabled = buttonEnabled ?: true,
                    onClick = { onClickActions.forEach { onAction(it) } },
                )
            }
            .bduiPadding(component.paddings)
    )
}

fun Modifier.bduiSize(
    width: BduiComponentSize,
    height: BduiComponentSize,
) = this
    .then(
        when (height) {
            is BduiComponentSize.Fixed -> Modifier.height(height.value.dp)
            BduiComponentSize.MatchParent -> Modifier.fillMaxHeight()
            else -> Modifier
        }
    )
    .then(
        when (width) {
            is BduiComponentSize.Fixed -> Modifier.width(width.value.dp)
            BduiComponentSize.MatchParent -> Modifier.fillMaxWidth()
            else -> Modifier
        }
    )

fun Modifier.bduiPadding(paddings: BduiComponentInsetsUi?): Modifier {
    return this.then(
        if (paddings != null) {
            Modifier.padding(
                start = paddings.start.dp,
                end = paddings.end.dp,
                top = paddings.top.dp,
                bottom = paddings.bottom.dp,
            )
        } else Modifier
    )
}
