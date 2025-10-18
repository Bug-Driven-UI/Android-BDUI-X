package ru.bugdrivenui.bduix.presentation.bdui_screen.local_state

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import javax.inject.Inject

private const val LOCAL_STATE_VARIABLE_PREFIX = "#{localStates."
private const val LOCAL_STATE_VARIABLE_SUFFIX = "}"

@ViewModelScoped
class LocalStateResolver @Inject constructor(
    private val localStateStore: LocalStateStore,
) {

    fun resolveRawPath(rawPath: String): Path? {
        return if (rawPath.startsWith(LOCAL_STATE_VARIABLE_PREFIX) && rawPath.endsWith(LOCAL_STATE_VARIABLE_SUFFIX)) {
            rawPath.substringAfter(LOCAL_STATE_VARIABLE_PREFIX).substringBeforeLast(LOCAL_STATE_VARIABLE_SUFFIX)
        } else null
    }

    fun resolveLocalStateRefs(
        jsonElement: JsonElement,
    ): JsonElement = when (jsonElement) {
        is JsonPrimitive -> {
            jsonElement.contentOrNull?.let { rawPath ->
                resolveRawPath(rawPath)?.let { path ->
                    localStateStore.get(path)
                } ?: jsonElement
            } ?: jsonElement
        }
        is JsonObject -> {
            JsonObject(
                content = jsonElement.mapValues { (_, value) -> resolveLocalStateRefs(value) }
            )
        }
        is JsonArray -> {
            JsonArray(
                content = jsonElement.map { resolveLocalStateRefs(it) }
            )
        }
    }
}