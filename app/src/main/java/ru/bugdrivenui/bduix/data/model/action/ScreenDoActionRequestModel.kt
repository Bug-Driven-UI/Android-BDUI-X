@file:OptIn(ExperimentalSerializationApi::class)

package ru.bugdrivenui.bduix.data.model.action

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlinx.serialization.json.JsonElement

@Serializable
data class ScreenDoActionRequestModel(
    @SerialName("actions") val actions: List<ActionRequestModel>,
)

@Serializable
@JsonClassDiscriminator("type")
sealed interface ActionRequestModel {

    @Serializable
    @SerialName("command")
    data class Command(
        @SerialName("name") val name: String,
        @SerialName("params") val params: Map<String, JsonElement>? = null,
    ) : ActionRequestModel

    @Serializable
    @SerialName("updateScreen")
    data class UpdateScreen(
        @SerialName("screenName") val screenName: String,
        @SerialName("hashes") val hashes: List<HashNode>,
        @SerialName("screenNavigationParams") val screenNavigationParams: Map<String, JsonElement>? = null,
    ) : ActionRequestModel
}

@Serializable
data class HashNode(
    @SerialName("id") val id: String,
    @SerialName("hash") val hash: String,
    @SerialName("children") val children: List<HashNode> = emptyList(),
)
