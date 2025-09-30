package ru.bugdrivenui.bduix.data.model.render

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ScreenRenderRequestModel(
    @SerialName("data") val data: Data,
) {

    @Serializable
    data class Data(
        @SerialName("screenName") val screenName: String,
        @SerialName("variables") val variables: Map<String, JsonElement>? = null,
    )
}
