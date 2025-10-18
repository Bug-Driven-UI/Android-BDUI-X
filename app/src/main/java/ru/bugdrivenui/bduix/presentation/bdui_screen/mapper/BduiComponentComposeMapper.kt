package ru.bugdrivenui.bduix.presentation.bdui_screen.mapper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiHorizontalAlignment
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiHorizontalAndVerticalAlignment
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiHorizontalArrangement
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiText
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiTextAlignment
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiTextDecorationType
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiVerticalAlignment
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiVerticalArrangement
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
        BduiTextDecorationType.STRIKETHROUGH -> TextDecoration.LineThrough
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

fun BduiHorizontalArrangement?.toCompose() = this?.let { arrangement ->
    when (arrangement) {
        BduiHorizontalArrangement.Center -> Arrangement.Center
        BduiHorizontalArrangement.End -> Arrangement.End
        BduiHorizontalArrangement.SpaceAround -> Arrangement.SpaceAround
        BduiHorizontalArrangement.SpaceBetween -> Arrangement.SpaceBetween
        BduiHorizontalArrangement.SpaceEvenly -> Arrangement.SpaceEvenly
        BduiHorizontalArrangement.Start -> Arrangement.Start
    }
} ?: Arrangement.Start

fun BduiVerticalAlignment?.toCompose() = this?.let { alignment ->
    when (alignment) {
        BduiVerticalAlignment.Bottom -> Alignment.Bottom
        BduiVerticalAlignment.Center -> Alignment.CenterVertically
        BduiVerticalAlignment.Top -> Alignment.Top
    }
} ?: Alignment.Top

fun BduiVerticalArrangement?.toCompose() = this?.let { arrangement ->
    when (arrangement) {
        BduiVerticalArrangement.Bottom -> Arrangement.Bottom
        BduiVerticalArrangement.Center -> Arrangement.Center
        BduiVerticalArrangement.SpaceAround -> Arrangement.SpaceAround
        BduiVerticalArrangement.SpaceBetween -> Arrangement.SpaceBetween
        BduiVerticalArrangement.SpaceEvenly -> Arrangement.SpaceEvenly
        BduiVerticalArrangement.Top -> Arrangement.Top
    }
} ?: Arrangement.Top

fun BduiHorizontalAlignment?.toCompose() = this?.let { alignment ->
    when (alignment) {
        BduiHorizontalAlignment.Center -> Alignment.CenterHorizontally
        BduiHorizontalAlignment.End -> Alignment.End
        BduiHorizontalAlignment.Start -> Alignment.Start
    }
} ?: Alignment.Start

fun BduiHorizontalAndVerticalAlignment?.toCompose() = this?.let { alignment ->
    when (alignment) {
        BduiHorizontalAndVerticalAlignment.BottomCenter -> Alignment.BottomCenter
        BduiHorizontalAndVerticalAlignment.BottomEnd -> Alignment.BottomEnd
        BduiHorizontalAndVerticalAlignment.BottomStart -> Alignment.BottomStart
        BduiHorizontalAndVerticalAlignment.Center -> Alignment.Center
        BduiHorizontalAndVerticalAlignment.CenterEnd -> Alignment.CenterEnd
        BduiHorizontalAndVerticalAlignment.CenterStart -> Alignment.CenterStart
        BduiHorizontalAndVerticalAlignment.TopCenter -> Alignment.TopCenter
        BduiHorizontalAndVerticalAlignment.TopEnd -> Alignment.TopEnd
        BduiHorizontalAndVerticalAlignment.TopStart -> Alignment.TopStart
    }
} ?: Alignment.TopStart

fun BduiTextAlignment?.toCompose() = this?.let { alignment ->
    when (alignment) {
        BduiTextAlignment.START -> TextAlign.Start
        BduiTextAlignment.CENTER -> TextAlign.Center
        BduiTextAlignment.END -> TextAlign.End
    }
} ?: TextAlign.Start
