package ru.bugdrivenui.bduix.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiShape

fun emptyString() = ""

fun Int?.orZero() = this ?: 0

fun Float?.orZero() = this ?: 0f

fun getEnabledAlpha(isEnabled: Boolean) = if (isEnabled) 1.0f else 0.65f

fun BduiShape?.toComposeShape() = when (this) {
    is BduiShape.RoundedCorners -> RoundedCornerShape(
        topStart = this.topStart.dp,
        topEnd = this.topEnd.dp,
        bottomStart = this.bottomStart.dp,
        bottomEnd = this.bottomEnd.dp,
    )

    null -> RectangleShape
}

fun String.pathToSegments(): List<String> =
    trim().trim('/').split('/').filter { it.isNotBlank() }
