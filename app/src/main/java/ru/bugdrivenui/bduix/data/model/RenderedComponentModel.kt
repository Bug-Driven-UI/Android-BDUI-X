@file:OptIn(ExperimentalSerializationApi::class)

package ru.bugdrivenui.bduix.data.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

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
    data class RenderedRowModel(
        @SerialName("children") val children: List<RenderedComponentModel>,
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel>,
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
    data class RenderedBoxModel(
        @SerialName("children") val children: List<RenderedComponentModel>,
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel>,
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
    data class RenderedColumnModel(
        @SerialName("children") val children: List<RenderedComponentModel>,
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel>,
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
    data class RenderedTextModel(
        @SerialName("textWithStyle") val textWithStyle: RenderedStyledTextRepresentationModel,
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel>,
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
    data class RenderedImageModel(
        @SerialName("imageUrl") val imageUrl: String,
        @SerialName("badge") val badge: RenderedBadgeModel,
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel>,
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
    data class RenderedInputModel(
        @SerialName("textWithStyle") val textWithStyle: RenderedStyledTextRepresentationModel,
        @SerialName("mask") val mask: Mask?,
        @SerialName("rightIcon") val rightIcon: RenderedImageModel?,
        @SerialName("regex") val regex: RenderedRegexModel?,
        @SerialName("placeholder") val placeholder: RenderedPlaceholderModel?,
        @SerialName("hint") val hint: RenderedHintModel?,
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel>,
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
    data class RenderedSpacerModel(
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel>,
        @SerialName("paddings") override val paddings: RenderedInsetsModel? = null,
        @SerialName("margins") override val margins: RenderedInsetsModel? = null,
        @SerialName("width") override val width: RenderedSizeModel,
        @SerialName("height") override val height: RenderedSizeModel,
        @SerialName("backgroundColor") override val backgroundColor: RenderedColorStyleModel? = null,
        @SerialName("border") override val border: RenderedBorderModel? = null,
        @SerialName("shape") override val shape: RenderedShapeModel? = null,
    ) : RenderedComponentModel

    @Serializable
    @SerialName("progressBar")
    data class RenderedProgressBar(
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel>,
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
    data class RenderedSwitch(
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel>,
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
    data class RenderedButtonModel(
        @SerialName("textWithStyle") val textWithStyle: RenderedStyledTextRepresentationModel,
        @SerialName("enabled") val enabled: Boolean,
        @SerialName("id") override val id: String,
        @SerialName("hash") override val hash: String,
        @SerialName("interactions") override val interactions: List<RenderedInteractionModel>,
        @SerialName("paddings") override val paddings: RenderedInsetsModel? = null,
        @SerialName("margins") override val margins: RenderedInsetsModel? = null,
        @SerialName("width") override val width: RenderedSizeModel,
        @SerialName("height") override val height: RenderedSizeModel,
        @SerialName("backgroundColor") override val backgroundColor: RenderedColorStyleModel? = null,
        @SerialName("border") override val border: RenderedBorderModel? = null,
        @SerialName("shape") override val shape: RenderedShapeModel? = null,
    ) : RenderedComponentModel
}