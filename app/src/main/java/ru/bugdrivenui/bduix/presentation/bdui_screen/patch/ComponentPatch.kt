package ru.bugdrivenui.bduix.presentation.bdui_screen.patch

import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi

data class ComponentPatch(
    val parentPath: String,
    val childId: String,
    val method: Method,
    val content: BduiComponentUi?,
) {
    enum class Method { INSERT, UPDATE, DELETE }
}

data class ComponentPatchGroup(
    val updates: MutableMap<String, BduiComponentUi> = mutableMapOf(),
    val inserts: LinkedHashMap<String, BduiComponentUi> = linkedMapOf(),
    val deletes: MutableSet<String> = mutableSetOf(),
)
