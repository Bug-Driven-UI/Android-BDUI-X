package ru.bugdrivenui.bduix.presentation.bdui_screen.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.bugdrivenui.bduix.presentation.bdui_screen.local_state.rememberTextOrLocalState
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toCompose
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toComposeTextStyle
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiTextDecorationType

@Composable
fun BduiTextComponent(
    component: BduiComponentUi.Text,
    modifier: Modifier,
) {
    val text = rememberTextOrLocalState(component.text.value)

    if (component.text.style.decorationType == BduiTextDecorationType.STRIKETHROUGH_RED) {
        RedStrikethroughText(
            text = text.value,
            modifier = modifier,
            style = component.text.toComposeTextStyle(),
            textAlign = component.text.textAlignment.toCompose(),
        )
    } else {
        Text(
            modifier = modifier,
            text = text.value,
            style = component.text.toComposeTextStyle(),
            textAlign = component.text.textAlignment.toCompose(),
        )
    }
}

@Composable
fun RedStrikethroughText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle,
    textAlign: TextAlign,
    color: Color = Color(0xFFFF4053),
    strokeWidth: Dp = (1.5).dp,
) {
    Box(modifier = modifier) {
        Text(
            modifier = modifier,
            text = text,
            style = style,
            textAlign = textAlign,
        )

        Canvas(modifier = Modifier.matchParentSize()) {
            // Определяем положение линии — примерно по центру текста
            val y = size.height / 2f
            drawLine(
                color = color,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = strokeWidth.toPx()
            )
        }
    }
}
