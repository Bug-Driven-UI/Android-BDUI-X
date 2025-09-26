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
    val backgroundColor: String?,
    val paddings: BduiComponentInsets,
    val margins: BduiComponentInsets,
    val width: BduiComponentSizeResponse,
    val height: BduiComponentSizeResponse,
    val interactions: List<BduiInteraction>?,
    val border: BduiBorderResponse?,
    val shape: BduiShapeResponse?,
)

data class BduiBorderResponse(
    val color: String,
    val thickness: Int,
)

data class BduiShapeResponse(
    val type: BduiShapeType,
    val topLeft: Int,
    val topRight: Int,
    val bottomLeft: Int,
    val bottomRight: Int,
)

enum class BduiShapeType {
    ROUNDED_CORNERS,
}

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

data class BduiComponentInsets(
    val start: Int,
    val end: Int,
    val top: Int,
    val bottom: Int,
)

data class BduiComponentSizeResponse(
    val type: BduiComponentSizeType,
    val fraction: Float?,
    val value: Int?,
)

enum class BduiComponentSizeType {
    FIXED,
    WEIGHTED,
    MATCH_PARENT,
    WRAP_CONTENT,
}

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
