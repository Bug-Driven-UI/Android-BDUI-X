package ru.bugdrivenui.bduix.presentation.bdui_screen.local_state

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import javax.inject.Inject

typealias LocalStates = Map<String, JsonElement>

class LocalStateStore @Inject constructor() {

    private val _localStates = MutableStateFlow<LocalStates>(emptyMap())
    val localStates = _localStates.asStateFlow()

    fun get(key: String): JsonElement? = _localStates.value[key]

    fun set(key: String, value: JsonElement) {
        _localStates.update { it + (key to value) }
    }

    fun setString(key: String, value: String) = set(key, JsonPrimitive(value))
}