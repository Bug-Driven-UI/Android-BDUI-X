package ru.bugdrivenui.bduix.data.model

// Это примерное тестовое представление ответа от бэка. На самом деле будет генерироваться OpenAPI Generator-ом
data class BduiScreenResponse(
    val screenId: String,
    val components: List<BduiComponentResponse>,
)

data class BduiComponentResponse(
    val id: String,
    val hash: String,
    val type: BduiComponentType,
    val children: List<BduiComponentResponse>?,
    val imageUrl: String?,
    val text: String?,
    val color: String?,
    val interactions: List<BduiInteraction>?,
)

data class BduiInteraction(
    val type: BduiInteractionType,
    val actions: List<BduiAction>,
)

data class BduiAction(
    val type: BduiActionType,
    val name: String?,
    val params: Map<String, String>?,
    val screenName: String?,
    val screenNavigationParams: Map<String, String>?,
)

enum class BduiComponentType {
    TEXT,
    IMAGE,
    BUTTON,
    INPUT,
    ROW,
    COLUMN,
    BOX,
}

enum class BduiInteractionType {
    ON_CLICK,
    ON_SHOW,
}

enum class BduiActionType {
    COMMAND,
    UPDATE_SCREEN,
}
