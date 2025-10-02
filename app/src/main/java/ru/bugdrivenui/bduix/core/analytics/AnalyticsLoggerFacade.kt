package ru.bugdrivenui.bduix.core.analytics

import android.util.Log

import ru.bugdrivenui.bduix.utils.emptyString
import javax.inject.Inject
import javax.inject.Singleton

private const val ANALYTICS_LOGGER_TAG = "AnalyticsLogger"

private const val EVENT_NAME_SCREEN_RENDERED = "screen_rendered"
private const val EVENT_NAME_SCREEN_SHOWN = "screen_shown"
private const val EVENT_NAME_SCREEN_UPDATED = "screen_updated"
private const val EVENT_NAME_COMPONENT_CLICKED = "component_clicked"
private const val EVENT_NAME_USER_NAVIGATED = "user_navigated"
private const val EVENT_NAME_ERROR_SCREEN_SHOWN = "error_screen_shown"
private const val EVENT_NAME_ERROR_SNACKBAR_SHOWN = "error_snackbar_shown"

private const val EVENT_PARAM_SCREEN_NAME = "screen_name"
private const val EVENT_PARAM_SCREEN_VERSION = "screen_version"
private const val EVENT_PARAM_COMPONENTS_COUNT = "components_count"
private const val EVENT_PARAM_RENDER_TIME_MS = "render_time_ms"
private const val EVENT_PARAM_UPDATED_COMPONENTS_COUNT = "updated_components_count"
private const val EVENT_PARAM_UPDATE_DURATION_MS = "update_duration_ms"
private const val EVENT_PARAM_COMPONENT_ID = "component_id"
private const val EVENT_PARAM_FROM_SCREEN_NAME = "from_screen_name"
private const val EVENT_PARAM_TO_SCREEN_NAME = "to_screen_name"
private const val EVENT_PARAM_METHOD = "method"
private const val EVENT_PARAM_MESSAGE = "message"

private const val MAX_MESSAGE_LENGTH = 100

private const val UNKNOWN_ERROR_MESSAGE = "unknown_error"

@Singleton
class AnalyticsLoggerFacade @Inject constructor(
    private val analyticsLogger: IAnalyticsLogger,
) : IAnalyticsLoggerFacade {

    override fun logScreenRendered(
        screenName: String,
        screenVersion: Int,
        componentsCount: Int,
        renderTimeMs: Long
    ) {
        analyticsLogger.logEvent(
            eventName = EVENT_NAME_SCREEN_RENDERED,
            params = mapOf(
                EVENT_PARAM_SCREEN_NAME to screenName,
                EVENT_PARAM_SCREEN_VERSION to screenVersion.toString(),
                EVENT_PARAM_COMPONENTS_COUNT to componentsCount.toString(),
                EVENT_PARAM_RENDER_TIME_MS to renderTimeMs.toString(),
            ),
        )

        Log.d(
            ANALYTICS_LOGGER_TAG,
            "logScreenRendered:" +
                    "screenName=$screenName," +
                    "version=$screenVersion," +
                    "components=$componentsCount," +
                    "renderTimeMs=$renderTimeMs"
        )
    }

    override fun logScreenShown(screenName: String) {
        analyticsLogger.logEvent(
            eventName = EVENT_NAME_SCREEN_SHOWN,
            params = mapOf(
                EVENT_PARAM_SCREEN_NAME to screenName,
            ),
        )

        Log.d(
            ANALYTICS_LOGGER_TAG,
            "logScreenShown: screenName=$screenName"
        )
    }

    override fun logScreenUpdated(
        screenName: String,
        updatedComponentsCount: Int,
        updateDurationMs: Long
    ) {
        analyticsLogger.logEvent(
            eventName = EVENT_NAME_SCREEN_UPDATED,
            params = mapOf(
                EVENT_PARAM_SCREEN_NAME to screenName,
                EVENT_PARAM_UPDATED_COMPONENTS_COUNT to updatedComponentsCount.toString(),
                EVENT_PARAM_UPDATE_DURATION_MS to updateDurationMs.toString(),
            ),
        )

        Log.d(
            ANALYTICS_LOGGER_TAG,
            "logScreenUpdated:" +
                    "screenName=$screenName," +
                    "updateCount=$updatedComponentsCount," +
                    "updateDurationMs=$updateDurationMs"
        )
    }

    override fun logComponentClicked(
        screenName: String,
        componentId: String,
    ) {
        analyticsLogger.logEvent(
            eventName = EVENT_NAME_COMPONENT_CLICKED,
            params = mapOf(
                EVENT_PARAM_SCREEN_NAME to screenName,
                EVENT_PARAM_COMPONENT_ID to componentId,
            ),
        )

        Log.d(
            ANALYTICS_LOGGER_TAG,
            "logComponentClicked:" +
                    "screenName=$screenName," +
                    "componentId=$componentId"
        )
    }

    override fun logUserNavigated(
        fromScreenName: String,
        toScreenName: String?,
        method: AnalyticsNavigationMethod
    ) {
        analyticsLogger.logEvent(
            eventName = EVENT_NAME_USER_NAVIGATED,
            params = mapOf(
                EVENT_PARAM_FROM_SCREEN_NAME to fromScreenName,
                EVENT_PARAM_TO_SCREEN_NAME to (toScreenName ?: emptyString()),
                EVENT_PARAM_METHOD to method.name,
            ),
        )

        Log.d(
            ANALYTICS_LOGGER_TAG,
            "logUserNavigated:" +
                    "fromScreenName=$fromScreenName," +
                    "toScreenName=${toScreenName ?: emptyString()}," +
                    "method=${method.name}"
        )
    }

    override fun logErrorScreenShown(screenName: String,) {
        analyticsLogger.logEvent(
            eventName = EVENT_NAME_ERROR_SCREEN_SHOWN,
            params = mapOf(
                EVENT_PARAM_SCREEN_NAME to screenName,
            ),
        )

        Log.d(
            ANALYTICS_LOGGER_TAG,
            "logErrorScreenShown: screenName=$screenName"
        )
    }

    override fun logErrorSnackbarShown(screenName: String, message: String?) {
        analyticsLogger.logEvent(
            eventName = EVENT_NAME_ERROR_SNACKBAR_SHOWN,
            params = mapOf(
                EVENT_PARAM_SCREEN_NAME to screenName,
                EVENT_PARAM_MESSAGE to (message ?: UNKNOWN_ERROR_MESSAGE).take(MAX_MESSAGE_LENGTH),
            ),
        )

        Log.d(
            ANALYTICS_LOGGER_TAG,
            "logErrorSnackbarShown:" +
                    "screenName=$screenName," +
                    "message=${(message ?: UNKNOWN_ERROR_MESSAGE).take(MAX_MESSAGE_LENGTH)}"
        )
    }
}