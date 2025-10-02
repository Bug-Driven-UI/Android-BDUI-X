package ru.bugdrivenui.bduix.core.analytics

import android.os.Bundle
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAnalyticsLogger @Inject constructor() : IAnalyticsLogger {

    private val analytics = Firebase.analytics

    override fun logEvent(
        eventName: String,
        params: Map<String, String>,
    ) {
        analytics.logEvent(eventName, params.toBundle())
    }

    private fun Map<String, String>.toBundle(): Bundle {
        val bundle = Bundle()
        forEach { (key, value) ->
            bundle.putString(key, value)
        }
        return bundle
    }
}