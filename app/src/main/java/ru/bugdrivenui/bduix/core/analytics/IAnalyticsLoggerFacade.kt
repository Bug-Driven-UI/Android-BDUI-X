package ru.bugdrivenui.bduix.core.analytics

interface IAnalyticsLoggerFacade {

    fun logScreenRendered(
        screenName: String,
        screenVersion: Int,
        componentsCount: Int,
        renderTimeMs: Long,
    )

    fun logScreenShown(
        screenName: String,
    )

    fun logScreenUpdated(
        screenName: String,
        updatedComponentsCount: Int,
        updateDurationMs: Long,
    )

    fun logComponentClicked(
        screenName: String,
        componentId: String,
    )

    fun logUserNavigated(
        fromScreenName: String,
        toScreenName: String?,
        method: AnalyticsNavigationMethod,
    )

    fun logErrorScreenShown(
        screenName: String,
    )
    
    fun logErrorSnackbarShown(
        screenName: String,
        message: String?,
    )
}