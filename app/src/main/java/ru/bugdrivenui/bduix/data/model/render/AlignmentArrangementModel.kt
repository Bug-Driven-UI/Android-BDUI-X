@file:OptIn(ExperimentalSerializationApi::class)

package ru.bugdrivenui.bduix.data.model.render

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed interface RenderedHorizontalArrangement {

    @Serializable
    @SerialName("start")
    data object Start : RenderedHorizontalArrangement

    @Serializable
    @SerialName("end")
    data object End : RenderedHorizontalArrangement

    @Serializable
    @SerialName("center")
    data object Center : RenderedHorizontalArrangement

    @Serializable
    @SerialName("spaceBetween")
    data object SpaceBetween : RenderedHorizontalArrangement

    @Serializable
    @SerialName("spaceEvenly")
    data object SpaceEvenly : RenderedHorizontalArrangement

    @Serializable
    @SerialName("spaceAround")
    data object SpaceAround : RenderedHorizontalArrangement
}

@Serializable
@JsonClassDiscriminator("type")
sealed interface RenderedVerticalArrangement {

    @Serializable
    @SerialName("top")
    data object Top : RenderedVerticalArrangement

    @Serializable
    @SerialName("bottom")
    data object Bottom : RenderedVerticalArrangement

    @Serializable
    @SerialName("center")
    data object Center : RenderedVerticalArrangement

    @Serializable
    @SerialName("spaceBetween")
    data object SpaceBetween : RenderedVerticalArrangement

    @Serializable
    @SerialName("spaceEvenly")
    data object SpaceEvenly : RenderedVerticalArrangement

    @Serializable
    @SerialName("spaceAround")
    data object SpaceAround : RenderedVerticalArrangement
}

@Serializable
@JsonClassDiscriminator("type")
sealed interface RenderedHorizontalAndVerticalAlignment {

    @Serializable
    @SerialName("topStart")
    data object TopStart : RenderedHorizontalAndVerticalAlignment

    @Serializable
    @SerialName("topCenter")
    data object TopCenter : RenderedHorizontalAndVerticalAlignment

    @Serializable
    @SerialName("topEnd")
    data object TopEnd : RenderedHorizontalAndVerticalAlignment

    @Serializable
    @SerialName("centerStart")
    data object CenterStart : RenderedHorizontalAndVerticalAlignment

    @Serializable
    @SerialName("center")
    data object Center : RenderedHorizontalAndVerticalAlignment

    @Serializable
    @SerialName("centerEnd")
    data object CenterEnd : RenderedHorizontalAndVerticalAlignment

    @Serializable
    @SerialName("bottomStart")
    data object BottomStart : RenderedHorizontalAndVerticalAlignment

    @Serializable
    @SerialName("bottomCenter")
    data object BottomCenter : RenderedHorizontalAndVerticalAlignment

    @Serializable
    @SerialName("bottomEnd")
    data object BottomEnd : RenderedHorizontalAndVerticalAlignment
}

@Serializable
@JsonClassDiscriminator("type")
sealed interface RenderedVerticalAlignment {

    @Serializable
    @SerialName("top")
    data object Top : RenderedVerticalAlignment

    @Serializable
    @SerialName("center")
    data object Center : RenderedVerticalAlignment

    @Serializable
    @SerialName("bottom")
    data object Bottom : RenderedVerticalAlignment
}

@Serializable
@JsonClassDiscriminator("type")
sealed interface RenderedHorizontalAlignment {

    @Serializable
    @SerialName("start")
    data object Start : RenderedHorizontalAlignment

    @Serializable
    @SerialName("center")
    data object Center : RenderedHorizontalAlignment

    @Serializable
    @SerialName("end")
    data object End : RenderedHorizontalAlignment
}