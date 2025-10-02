package ru.bugdrivenui.bduix.core.analytics

interface IAnalyticsLogger {

    fun logEvent(
        eventName: String,
        params: Map<String, String>,
    )
}