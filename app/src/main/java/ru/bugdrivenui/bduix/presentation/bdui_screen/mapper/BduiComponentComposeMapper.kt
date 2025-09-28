package ru.bugdrivenui.bduix.presentation.bdui_screen.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiText
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiTextDecorationType
import ru.bugdrivenui.bduix.presentation.ui.theme.ManropeFont

fun BduiColor.toComposeColor(fallbackColor: BduiColor = BduiColor.Default): Color {
    return runCatching { Color(this.hex.toColorInt()) }
        .getOrElse { Color(fallbackColor.hex.toColorInt()) }
}

fun Int.toComposeFontWeight(): FontWeight {
    return when (this) {
        in 0..250 -> FontWeight.ExtraLight
        in 251..350 -> FontWeight.Light
        in 351..450 -> FontWeight.Normal
        in 451..550 -> FontWeight.Medium
        in 551..650 -> FontWeight.SemiBold
        in 651..750 -> FontWeight.Bold
        in 750..1000 -> FontWeight.ExtraBold
        else -> FontWeight.Normal
    }
}

// TODO отрефакторить к единому тексту по всем компонентам
fun BduiText.toComposeTextStyle(): TextStyle {
    val weight = style.weight.toComposeFontWeight()
    val decoration = when (style.decorationType) {
        BduiTextDecorationType.UNDERLINE -> TextDecoration.Underline
        BduiTextDecorationType.STRIKETHROUGH,
        BduiTextDecorationType.STRIKETHROUGH_RED -> TextDecoration.LineThrough
        else -> null
    }
    return TextStyle(
        fontSize = style.size.sp,
        fontStyle = if (style.decorationType == BduiTextDecorationType.ITALIC) {
            FontStyle.Italic
        } else {
            FontStyle.Normal
        },
        fontWeight = weight,
        textDecoration = decoration,
        color = color.toComposeColor(),
        fontFamily = ManropeFont,
    )
}

