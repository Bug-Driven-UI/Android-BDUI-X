package ru.bugdrivenui.bduix.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun BduiTheme(
    colors: BduiColors = getBduiColors(),
    typography: BduiTypography = getBduiTypography(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalBduiColors provides colors,
        LocalBduiTypography provides typography,
    ) {
        content()
    }
}

object BduiTheme {

    val colors: BduiColors
        @Composable
        get() = LocalBduiColors.current

    val typography: BduiTypography
        @Composable
        get() = LocalBduiTypography.current
}
