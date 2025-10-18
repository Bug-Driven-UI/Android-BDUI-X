package ru.bugdrivenui.bduix.presentation.bdui_screen.local_state

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import javax.inject.Inject

/**
 * Путь до переменной локального состояния в формате "user.fullName"
 */
typealias Path = String
typealias LocalStates = Map<Path, JsonPrimitive>

@ViewModelScoped
class LocalStateStore @Inject constructor() {

    private val _localStates = MutableStateFlow<LocalStates>(emptyMap())
    val localStates = _localStates.asStateFlow()

    fun get(path: Path): JsonPrimitive? = _localStates.value[path]

    fun set(path: Path, value: JsonPrimitive) {
        _localStates.update { it + (path to value) }
    }

    fun setAll(
        rawStates: Map<Path, JsonElement>,
    ) {
        _localStates.update { flattenJsonMap(rawStates) }
    }

    fun setString(path: Path, value: String) {
        _localStates.update { it + (path to JsonPrimitive(value)) }
    }

    fun clear() {
        _localStates.update { emptyMap() }
    }

    private fun flattenJsonMap(
        rawStates: Map<Path, JsonElement>,
        parentKey: String? = null,
    ): Map<Path, JsonPrimitive> {
        val states = mutableMapOf<Path, JsonPrimitive>()
        rawStates.forEach { (key, value) ->
            val fullKey = if (parentKey != null) "$parentKey.$key" else key
            when (value) {
                is JsonPrimitive -> states[fullKey] = value
                is JsonObject -> states.putAll(flattenJsonMap(value, fullKey))
                is JsonArray -> Unit // not supported
            }
        }
        return states
    }
}