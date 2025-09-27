package ru.bugdrivenui.bduix.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import ru.bugdrivenui.bduix.presentation.screen.mapper.toComposeColor
import ru.bugdrivenui.bduix.presentation.screen.model.BduiComponentInsetsUi
import ru.bugdrivenui.bduix.presentation.screen.model.BduiComponentSize
import ru.bugdrivenui.bduix.presentation.screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.screen.model.BduiShape

inline fun <T : Any> Modifier.ifNotNull(
    value: T?,
    block: Modifier.(T) -> Modifier
): Modifier =
    if (value != null) block(this, value) else this

fun Modifier.bduiBaseProperties(component: BduiComponentUi): Modifier {
    val componentShape = component.shape
    var shape = RectangleShape
    if (componentShape != null) {
        shape = when (componentShape) {
            is BduiShape.RoundedCorners -> RoundedCornerShape(
                topStart = componentShape.topStart.dp,
                topEnd = componentShape.topEnd.dp,
                bottomStart = componentShape.bottomStart.dp,
                bottomEnd = componentShape.bottomEnd.dp,
            )
        }
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
