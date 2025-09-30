package ru.bugdrivenui.bduix.utils

fun emptyString() = ""

fun Int?.orZero() = this ?: 0

fun Float?.orZero() = this ?: 0f

fun getEnabledAlpha(isEnabled: Boolean) = if (isEnabled) 1.0f else 0.65f

fun String.pathToSegments(): List<String> =
    trim().trim('/').split('/').filter { it.isNotBlank() }
