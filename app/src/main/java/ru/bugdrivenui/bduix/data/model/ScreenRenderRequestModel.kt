package ru.bugdrivenui.bduix.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScreenRenderRequestModel(
    @SerialName("data") val data: Data,
) {

    @Serializable
    data class Data(
        @SerialName("screenName") val screenName: String,
        @SerialName("variables") val variables: Map<String, String>? = null,
    )
}
