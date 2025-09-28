@file:OptIn(ExperimentalSerializationApi::class)

package ru.bugdrivenui.bduix.data.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlinx.serialization.json.JsonElement

@Serializable
data class RenderedScreenResponseModel(
    @SerialName("screen") val screen: RenderedScreenModel,
)

@Serializable
data class RenderedScreenModel(
    @SerialName("screenName") val screenName: String,
    @SerialName("version") val version: Int,
    @SerialName("components") val components: List<RenderedComponentModel>,
    @SerialName("scaffold") val scaffold: RenderedScaffoldModel? = null,
)

@Serializable
data class RenderedScaffoldModel(
    @SerialName("topBar") val topBar: RenderedComponentModel? = null,
    @SerialName("bottomBar") val bottomBar: RenderedComponentModel? = null,
)

@Serializable
data class RenderedInteractionModel(
    @SerialName("type") val type: Type,
    @SerialName("actions") val actions: List<RenderedActionModel>,
) {

    @Serializable
    enum class Type {
        @SerialName("onClick") ON_CLICK,
        @SerialName("onShow") ON_SHOW,
    }
}

@Serializable
@JsonClassDiscriminator("type")
sealed interface RenderedActionModel {

    @Serializable
    @SerialName("command")
    data class RenderedCommandActionModel(
        @SerialName("name") val name: String,
        @SerialName("params") val params: Map<String, JsonElement>? = null,
    ) : RenderedActionModel

    @Serializable
    @SerialName("updateScreen")
    data class RenderedUpdateScreenActionModel(
        @SerialName("screenName") val screenName: String,
        @SerialName("screenNavigationParams") val screenNavigationParams: Map<String, JsonElement>? = null,
    ) : RenderedActionModel
}

@Serializable
data class RenderedInsetsModel(
    @SerialName("left") val left: Int,
    @SerialName("right") val right: Int,
    @SerialName("bottom") val bottom: Int,
    @SerialName("top") val top: Int,
)

@Serializable
@JsonClassDiscriminator("type")
sealed interface RenderedSizeModel {

    @Serializable
    @SerialName("fixed")
    data class RenderedSizeFixedModel(
        @SerialName("value") val value: Int,
    ) : RenderedSizeModel

    @Serializable
    @SerialName("weighted")
    data class RenderedSizeWeightedModel(
        @SerialName("fraction") val fraction: Float,
    ) : RenderedSizeModel

    @Serializable
    @SerialName("matchParent")
    data object RenderedSizeMatchParentModel : RenderedSizeModel

    @Serializable
    @SerialName("wrapContent")
    data object RenderedSizeWrapContentModel : RenderedSizeModel
}

@Serializable
data class RenderedColorStyleModel(
    @SerialName("hex") val hex: String,
)

@Serializable
data class RenderedBorderModel(
    @SerialName("color") val color: RenderedColorStyleModel,
    @SerialName("thickness") val thickness: Int,
)

@Serializable
data class RenderedShapeModel(
    @SerialName("type") val type: Type,
    @SerialName("topRight") val topRight: Int,
    @SerialName("topLeft") val topLeft: Int,
    @SerialName("bottomRight") val bottomRight: Int,
    @SerialName("bottomLeft") val bottomLeft: Int,
) {
    @Serializable
    enum class Type {
        @SerialName("roundedCorners") ROUNDED_CORNERS,
    }
}

@Serializable
data class RenderedStyledTextRepresentationModel(
    @SerialName("text") val text: String,
    @SerialName("textStyle") val textStyle: RenderedTextStyleModel,
    @SerialName("colorStyle") val textColorStyle: RenderedColorStyleModel,
)

@Serializable
data class RenderedTextStyleModel(
    @SerialName("decoration") val decoration: RenderedTextDecorationTypeModel? = null,
    @SerialName("weight") val weight: Int? = null,
    @SerialName("size") val size: Int,
    @SerialName("lineHeight") val lineHeight: Int,
)

@Serializable
enum class RenderedTextDecorationTypeModel {
    @SerialName("italic") ITALIC,
    @SerialName("underline") UNDERLINE,
    @SerialName("strikeThrough") STRIKETHROUGH,
    @SerialName("strikeThroughRed") STRIKETHROUGH_RED,
    @SerialName("regular") REGULAR,
}

@Serializable
@JsonClassDiscriminator("type")
sealed interface RenderedBadgeModel {

    @Serializable
    @SerialName("badgeWithImage")
    data class RenderedBadgeWithImageModel(
        @SerialName("imageUrl") val imageUrl: String,
    ) : RenderedBadgeModel

    @Serializable
    @SerialName("badgeWithText")
    data class RenderedBadgeWithTextModel(
        @SerialName("textWithStyle") val textWithStyle: RenderedStyledTextRepresentationModel,
    ) : RenderedBadgeModel
}

@Serializable
enum class RenderedRegexModel {
    @SerialName("email") EMAIL,
}

@Serializable
data class RenderedPlaceholderModel(
    @SerialName("textWithStyle") val textWithStyle: RenderedStyledTextRepresentationModel,
)

@Serializable
data class RenderedHintModel(
    @SerialName("textWithStyle") val textWithStyle: RenderedStyledTextRepresentationModel,
)
