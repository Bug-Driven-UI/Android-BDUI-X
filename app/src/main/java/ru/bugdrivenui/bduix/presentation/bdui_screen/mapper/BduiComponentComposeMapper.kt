package ru.bugdrivenui.bduix.presentation.bdui_screen.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.core.graphics.toColorInt
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiColor

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
