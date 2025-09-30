package ru.bugdrivenui.bduix.presentation.bdui_screen.mapper

import ru.bugdrivenui.bduix.data.model.RenderedActionModel
import ru.bugdrivenui.bduix.data.model.RenderedBorderModel
import ru.bugdrivenui.bduix.data.model.RenderedColorStyleModel
import ru.bugdrivenui.bduix.data.model.RenderedInsetsModel
import ru.bugdrivenui.bduix.data.model.RenderedInteractionModel
import ru.bugdrivenui.bduix.data.model.RenderedShapeModel
import ru.bugdrivenui.bduix.data.model.RenderedSizeModel
import ru.bugdrivenui.bduix.data.model.RenderedStyledTextRepresentationModel
import ru.bugdrivenui.bduix.data.model.RenderedTextDecorationTypeModel
import ru.bugdrivenui.bduix.data.model.RenderedTextStyleModel
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiActionUi.*
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiBorder
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentInsetsUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentInteractionsUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentSize
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiShape
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiText
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiTextDecorationType
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiTextStyle
import ru.bugdrivenui.bduix.presentation.utils.PresentationConstants.DEFAULT_TEXT_COLOR_HEX
import ru.bugdrivenui.bduix.utils.orZero

fun RenderedColorStyleModel?.toBduiColor(
    fallbackColor: BduiColor = BduiColor.Default,
): BduiColor {
    return BduiColor(hex = this?.hex ?: fallbackColor.hex)
}

fun List<RenderedInteractionModel>.toBduiInteractions(): BduiComponentInteractionsUi {
    return BduiComponentInteractionsUi(
        onClick = this.toBduiInteractionActions(
            interactionType = RenderedInteractionModel.Type.ON_CLICK,
        ),
        onShow = this.toBduiInteractionActions(
            interactionType = RenderedInteractionModel.Type.ON_SHOW,
        ),
    )
}

fun List<RenderedInteractionModel>.toBduiInteractionActions(
    interactionType: RenderedInteractionModel.Type,
): List<BduiActionUi>? {
    return this
        .find { it.type == interactionType }
        ?.toBduiInteractionActions()
}

fun RenderedInteractionModel.toBduiInteractionActions(): List<BduiActionUi> {
    val actions = mutableListOf<BduiActionUi>()
    val remoteActions = mutableListOf<Remote>()
    this.actions.forEach { action ->
        when (action) {
            is RenderedActionModel.RenderedCommandActionModel -> {
                remoteActions.add(
                    Command(
                        name = action.name,
                        params = action.params,
                    )
                )
            }

            is RenderedActionModel.RenderedUpdateScreenActionModel -> {
                remoteActions.add(
                    UpdateScreen(
                        screenName = action.screenName,
                        screenNavigationParams = action.screenNavigationParams,
                    )
                )
            }

            RenderedActionModel.RenderedNavigateBackActionModel -> {
                actions.add(NavigateBack)
            }

            is RenderedActionModel.RenderedNavigateToActionModel -> {
                actions.add(
                    NavigateTo(
                        screenName = action.screenName,
                        screenNavigationParams = action.screenNavigationParams,
                    )
                )
            }
        }
    }
    actions.add(
        SendRemoteActions(
            actions = remoteActions,
        )
    )
    return actions
}

fun RenderedInsetsModel?.toComponentInsets(): BduiComponentInsetsUi {
    return BduiComponentInsetsUi(
        start = this?.left.orZero(),
        end = this?.right.orZero(),
        top = this?.top.orZero(),
        bottom = this?.bottom.orZero(),
    )
}

fun RenderedSizeModel.toComponentSize(): BduiComponentSize {
    return when (this) {
        is RenderedSizeModel.RenderedSizeFixedModel -> BduiComponentSize.Fixed(this.value)
        RenderedSizeModel.RenderedSizeMatchParentModel -> BduiComponentSize.MatchParent
        is RenderedSizeModel.RenderedSizeWeightedModel -> BduiComponentSize.Weighted(this.fraction)
        RenderedSizeModel.RenderedSizeWrapContentModel -> BduiComponentSize.WrapContent
    }
}

fun RenderedStyledTextRepresentationModel.toBduiText(): BduiText {
    return BduiText(
        value = this.text,
        color = this.textColorStyle.toBduiColor(
            fallbackColor = BduiColor(DEFAULT_TEXT_COLOR_HEX),
        ),
        style = this.textStyle.toBduiTextStyle(),
    )
}

fun RenderedTextStyleModel.toBduiTextStyle(): BduiTextStyle {
    return BduiTextStyle(
        decorationType = when (this.decoration) {
            RenderedTextDecorationTypeModel.ITALIC -> BduiTextDecorationType.ITALIC
            RenderedTextDecorationTypeModel.UNDERLINE -> BduiTextDecorationType.UNDERLINE
            RenderedTextDecorationTypeModel.STRIKETHROUGH -> BduiTextDecorationType.STRIKETHROUGH
            RenderedTextDecorationTypeModel.STRIKETHROUGH_RED -> BduiTextDecorationType.STRIKETHROUGH_RED
            RenderedTextDecorationTypeModel.REGULAR -> BduiTextDecorationType.REGULAR
            null -> BduiTextDecorationType.REGULAR
        },
        weight = this.weight ?: 400,
        size = this.size,
    )
}

fun RenderedBorderModel?.toBduiBorder(): BduiBorder? {
    return this?.let { border ->
        BduiBorder(
            color = border.color.toBduiColor(),
            thickness = border.thickness,
        )
    }
}

fun RenderedShapeModel?.toBduiShape(): BduiShape? {
    return this?.let { shape ->
        when (shape.type) {
            RenderedShapeModel.Type.ROUNDED_CORNERS -> {
                BduiShape.RoundedCorners(
                    topStart = shape.topLeft,
                    topEnd = shape.topRight,
                    bottomStart = shape.bottomLeft,
                    bottomEnd = shape.bottomRight,
                )
            }
        }
    }
}
