package ru.bugdrivenui.bduix.presentation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.bugdrivenui.bduix.R

class BduiTypography(
    val H20_H2: TextStyle,
    val M10_P: TextStyle,
    val M20_P: TextStyle,
)

val ManropeFont = FontFamily(
    Font(R.font.manrope_extralight, FontWeight.ExtraLight), // 200
    Font(R.font.manrope_light, FontWeight.Light),           // 300
    Font(R.font.manrope_regular, FontWeight.Normal),        // 400
    Font(R.font.manrope_medium, FontWeight.Medium),         // 500
    Font(R.font.manrope_semibold, FontWeight.SemiBold),     // 600
    Font(R.font.manrope_bold, FontWeight.Bold),             // 700
    Font(R.font.manrope_extrabold, FontWeight.ExtraBold),   // 800
)

fun getBduiTypography(): BduiTypography {
    return BduiTypography(
        H20_H2 = TextStyle(
            fontFamily = ManropeFont,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 26.sp,
            lineHeight = 30.sp,
        ),
        M10_P = TextStyle(
            fontFamily = ManropeFont,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            lineHeight = 22.sp,
        ),
        M20_P = TextStyle(
            fontFamily = ManropeFont,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            lineHeight = 20.sp,
        ),
    )
}

val LocalBduiTypography = staticCompositionLocalOf<BduiTypography> {
    error("CompositionLocal LocalBduiTypography not present")
}
