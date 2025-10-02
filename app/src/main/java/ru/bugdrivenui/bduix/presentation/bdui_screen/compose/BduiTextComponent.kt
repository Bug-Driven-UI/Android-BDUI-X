package ru.bugdrivenui.bduix.presentation.bdui_screen.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toComposeColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toComposeFontWeight
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiTextDecorationType
import ru.bugdrivenui.bduix.presentation.ui.theme.ManropeFont

@Composable
fun BduiTextComponent(
    component: BduiComponentUi.Text,
    modifier: Modifier,
) {
    val textColor = component.text.color.toComposeColor()
    val fontSize = component.text.style.size.sp
    val fontWeight = component.text.style.weight.toComposeFontWeight()
    val fontStyle = if (component.text.style.decorationType == BduiTextDecorationType.ITALIC) {
        FontStyle.Italic
    } else {
        FontStyle.Normal
    }

    Text(
        modifier = modifier,
        text = component.text.value,
        textDecoration = when (component.text.style.decorationType) {
            BduiTextDecorationType.UNDERLINE -> TextDecoration.Underline
            BduiTextDecorationType.STRIKETHROUGH -> TextDecoration.LineThrough
            BduiTextDecorationType.STRIKETHROUGH_RED -> TextDecoration.LineThrough
            else -> TextDecoration.None
        },
        color = textColor,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = ManropeFont,
    )

    if (component.text.style.decorationType == BduiTextDecorationType.STRIKETHROUGH_RED) {
        // TODO отдельный компонент под RedStrokethroughText
    } else {
        // TODO обычный текст
    }
}