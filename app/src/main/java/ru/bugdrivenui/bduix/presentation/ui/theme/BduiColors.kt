package ru.bugdrivenui.bduix.presentation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class BduiColors(
    val text: BduiTextColors,
    val component: BduiComponentColors,
)

class BduiTextColors(
    val primary: Color,
    val inversePrimary: Color,
)

class BduiComponentColors(
    val button: BduiButtonColors,
    val toast: BduiToastColors,
)

class BduiButtonColors(
    val bg: BduiButtonBackgroundColors,
    val text: BduiButtonTextColors,
)

class BduiButtonBackgroundColors(
    val primary: Color,
)

class BduiButtonTextColors(
    val primary: Color,
)

class BduiToastColors(
    val default: Color,
)

fun getBduiColors(): BduiColors {
    return BduiColors(
        text = getBduiTextColors(),
        component = getBduiComponentColors(),
    )
}

private fun getBduiTextColors(): BduiTextColors {
    return BduiTextColors(
        primary = Color(0xFF000000),
        inversePrimary = Color(0xFFFFFFFF),
    )
}

private fun getBduiComponentColors(): BduiComponentColors {
    return BduiComponentColors(
        button = getBduiButtonColors(),
        toast = getBduiToastColors(),
    )
}

private fun getBduiButtonColors(): BduiButtonColors {
    return BduiButtonColors(
        bg = getBduiButtonBackgroundColors(),
        text = getBduiButtonTextColors(),
    )
}

private fun getBduiButtonBackgroundColors(): BduiButtonBackgroundColors {
    return BduiButtonBackgroundColors(
        primary = Color(0xFF141414),
    )
}

private fun getBduiButtonTextColors(): BduiButtonTextColors {
    return BduiButtonTextColors(
        primary = Color(0xFFFFFFFF),
    )
}

private fun getBduiToastColors(): BduiToastColors {
    return BduiToastColors(
        default = Color(0xFF141414),
    )
}

val LocalBduiColors = staticCompositionLocalOf<BduiColors> {
    error("CompositionLocal LocalBduiColors not present")
}
