@file:OptIn(ExperimentalSerializationApi::class)

package ru.bugdrivenui.bduix.data.model.action

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import ru.bugdrivenui.bduix.data.model.render.RenderedComponentModel

@Serializable
data class ScreenDoActionResponseModel(
    @SerialName("responses") val responses: List<ActionResponseModel>,
)

@Serializable
@JsonClassDiscriminator("type")
sealed interface ActionResponseModel {

    @Serializable
    @SerialName("command")
    data class Command(
        @SerialName("name") val name: String,
        @SerialName("response") val response: Response,
    ) : ActionResponseModel {

        @Serializable
        data class Response(
            @SerialName("data") val data: Data,
        ) {

            @Serializable
            data class Data(
                @SerialName("component") val component: RenderedComponentModel? = null,
                @SerialName("fallbackMessage") val fallbackMessage: String? = null,
            )
        }
    }

    @Serializable
    @SerialName("updateScreen")
    data class UpdateScreen(
        @SerialName("response") val response: Response,
    ) : ActionResponseModel {

        @Serializable
        data class Response(
            @SerialName("data") val data: List<Data>,
        ) {

            @Serializable
            data class Data(
                @SerialName("target") val target: String,
                @SerialName("method") val method: ActionMethod,
                @SerialName("content") val content: RenderedComponentModel? = null,
            ) {

                @Serializable
                enum class ActionMethod {
                    @SerialName("update") UPDATE,
                    @SerialName("insert") INSERT,
                    @SerialName("delete") DELETE,
                }
            }
        }
    }
}
