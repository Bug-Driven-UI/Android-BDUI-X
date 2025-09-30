@file:OptIn(ExperimentalSerializationApi::class)

package ru.bugdrivenui.bduix.data.model.render

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import ru.bugdrivenui.bduix.data.model.RenderedBadgeModel
import ru.bugdrivenui.bduix.data.model.RenderedBorderModel
import ru.bugdrivenui.bduix.data.model.RenderedColorStyleModel
import ru.bugdrivenui.bduix.data.model.RenderedHintModel
import ru.bugdrivenui.bduix.data.model.RenderedInsetsModel
import ru.bugdrivenui.bduix.data.model.RenderedInteractionModel
import ru.bugdrivenui.bduix.data.model.RenderedPlaceholderModel
import ru.bugdrivenui.bduix.data.model.RenderedRegexModel
import ru.bugdrivenui.bduix.data.model.RenderedShapeModel
import ru.bugdrivenui.bduix.data.model.RenderedSizeModel
import ru.bugdrivenui.bduix.data.model.RenderedStyledTextRepresentationModel

@Serializable
@JsonClassDiscriminator("type")
sealed interface RenderedComponentModel {
    val id: String
    val hash: String
    val interactions: List<RenderedInteractionModel>
    val paddings: RenderedInsetsModel?
    val margins: RenderedInsetsModel?
    val width: RenderedSizeModel
    val height: RenderedSizeModel
    val backgroundColor: RenderedColorStyleModel?
    val border: RenderedBorderModel?
    val shape: RenderedShapeModel?

    @Serializable
    @SerialName("row")
    data class Row(
        @SerialName("children") val children: List<RenderedComponentModel>,
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel> = emptyList(),
        @SerialName("paddings") override val paddings: RenderedInsetsModel? = null,
        @SerialName("margins") override val margins: RenderedInsetsModel? = null,
        @SerialName("width") override val width: RenderedSizeModel,
        @SerialName("height") override val height: RenderedSizeModel,
        @SerialName("backgroundColor") override val backgroundColor: RenderedColorStyleModel? = null,
        @SerialName("border") override val border: RenderedBorderModel? = null,
        @SerialName("shape") override val shape: RenderedShapeModel? = null,
    ) : RenderedComponentModel

    @Serializable
    @SerialName("box")
    data class Box(
        @SerialName("children") val children: List<RenderedComponentModel>,
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel> = emptyList(),
        @SerialName("paddings") override val paddings: RenderedInsetsModel? = null,
        @SerialName("margins") override val margins: RenderedInsetsModel? = null,
        @SerialName("width") override val width: RenderedSizeModel,
        @SerialName("height") override val height: RenderedSizeModel,
        @SerialName("backgroundColor") override val backgroundColor: RenderedColorStyleModel? = null,
        @SerialName("border") override val border: RenderedBorderModel? = null,
        @SerialName("shape") override val shape: RenderedShapeModel? = null,
    ) : RenderedComponentModel

    @Serializable
    @SerialName("column")
    data class Column(
        @SerialName("children") val children: List<RenderedComponentModel>,
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel> = emptyList(),
        @SerialName("paddings") override val paddings: RenderedInsetsModel? = null,
        @SerialName("margins") override val margins: RenderedInsetsModel? = null,
        @SerialName("width") override val width: RenderedSizeModel,
        @SerialName("height") override val height: RenderedSizeModel,
        @SerialName("backgroundColor") override val backgroundColor: RenderedColorStyleModel? = null,
        @SerialName("border") override val border: RenderedBorderModel? = null,
        @SerialName("shape") override val shape: RenderedShapeModel? = null,
    ) : RenderedComponentModel

    @Serializable
    @SerialName("text")
    data class Text(
        @SerialName("textWithStyle") val textWithStyle: RenderedStyledTextRepresentationModel,
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel> = emptyList(),
        @SerialName("paddings") override val paddings: RenderedInsetsModel? = null,
        @SerialName("margins") override val margins: RenderedInsetsModel? = null,
        @SerialName("width") override val width: RenderedSizeModel,
        @SerialName("height") override val height: RenderedSizeModel,
        @SerialName("backgroundColor") override val backgroundColor: RenderedColorStyleModel? = null,
        @SerialName("border") override val border: RenderedBorderModel? = null,
        @SerialName("shape") override val shape: RenderedShapeModel? = null,
    ) : RenderedComponentModel

    @Serializable
    @SerialName("image")
    data class Image(
        @SerialName("imageUrl") val imageUrl: String,
        @SerialName("badge") val badge: RenderedBadgeModel,
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel> = emptyList(),
        @SerialName("paddings") override val paddings: RenderedInsetsModel? = null,
        @SerialName("margins") override val margins: RenderedInsetsModel? = null,
        @SerialName("width") override val width: RenderedSizeModel,
        @SerialName("height") override val height: RenderedSizeModel,
        @SerialName("backgroundColor") override val backgroundColor: RenderedColorStyleModel? = null,
        @SerialName("border") override val border: RenderedBorderModel? = null,
        @SerialName("shape") override val shape: RenderedShapeModel? = null,
    ) : RenderedComponentModel

    @Serializable
    @SerialName("input")
    data class Input(
        @SerialName("textWithStyle") val textWithStyle: RenderedStyledTextRepresentationModel,
        @SerialName("mask") val mask: Mask?,
        @SerialName("rightIcon") val rightIcon: Image?,
        @SerialName("regex") val regex: RenderedRegexModel?,
        @SerialName("placeholder") val placeholder: RenderedPlaceholderModel?,
        @SerialName("hint") val hint: RenderedHintModel?,
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel> = emptyList(),
        @SerialName("paddings") override val paddings: RenderedInsetsModel? = null,
        @SerialName("margins") override val margins: RenderedInsetsModel? = null,
        @SerialName("width") override val width: RenderedSizeModel,
        @SerialName("height") override val height: RenderedSizeModel,
        @SerialName("backgroundColor") override val backgroundColor: RenderedColorStyleModel? = null,
        @SerialName("border") override val border: RenderedBorderModel? = null,
        @SerialName("shape") override val shape: RenderedShapeModel? = null,
    ) : RenderedComponentModel {

        @Serializable
        enum class Mask {
            @SerialName("phone") PHONE,
        }
    }

    @Serializable
    @SerialName("spacer")
    data class Spacer(
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel> = emptyList(),
        @SerialName("paddings") override val paddings: RenderedInsetsModel? = null,
        @SerialName("margins") override val margins: RenderedInsetsModel? = null,
        @SerialName("width") override val width: RenderedSizeModel,
        @SerialName("height") override val height: RenderedSizeModel,
        @SerialName("backgroundColor") override val backgroundColor: RenderedColorStyleModel? = null,
        @SerialName("border") override val border: RenderedBorderModel? = null,
        @SerialName("shape") override val shape: RenderedShapeModel? = null,
    ) : RenderedComponentModel

    @Serializable
    @SerialName("switch")
    data class Switch(
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel> = emptyList(),
        @SerialName("paddings") override val paddings: RenderedInsetsModel? = null,
        @SerialName("margins") override val margins: RenderedInsetsModel? = null,
        @SerialName("width") override val width: RenderedSizeModel,
        @SerialName("height") override val height: RenderedSizeModel,
        @SerialName("backgroundColor") override val backgroundColor: RenderedColorStyleModel? = null,
        @SerialName("border") override val border: RenderedBorderModel? = null,
        @SerialName("shape") override val shape: RenderedShapeModel? = null,
    ) : RenderedComponentModel

    @Serializable
    @SerialName("button")
    data class Button(
        @SerialName("textWithStyle") val textWithStyle: RenderedStyledTextRepresentationModel,
        @SerialName("enabled") val enabled: Boolean,
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel> = emptyList(),
        @SerialName("paddings") override val paddings: RenderedInsetsModel? = null,
        @SerialName("margins") override val margins: RenderedInsetsModel? = null,
        @SerialName("width") override val width: RenderedSizeModel,
        @SerialName("height") override val height: RenderedSizeModel,
        @SerialName("backgroundColor") override val backgroundColor: RenderedColorStyleModel? = null,
        @SerialName("border") override val border: RenderedBorderModel? = null,
        @SerialName("shape") override val shape: RenderedShapeModel? = null,
    ) : RenderedComponentModel
}