package ru.bugdrivenui.bduix.presentation.bdui_screen.mapper

import ru.bugdrivenui.bduix.data.model.BduiActionType
import ru.bugdrivenui.bduix.data.model.BduiBorderResponse
import ru.bugdrivenui.bduix.data.model.BduiComponentInsets
import ru.bugdrivenui.bduix.data.model.BduiComponentSizeResponse
import ru.bugdrivenui.bduix.data.model.BduiComponentSizeType
import ru.bugdrivenui.bduix.data.model.BduiInteraction
import ru.bugdrivenui.bduix.data.model.BduiInteractionType
import ru.bugdrivenui.bduix.data.model.BduiShapeResponse
import ru.bugdrivenui.bduix.data.model.BduiShapeType
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiBorder
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentInsetsUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentInteractionsUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentSize
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiShape
import ru.bugdrivenui.bduix.utils.orZero


fun String?.toBduiColor(
    fallbackColor: BduiColor = BduiColor.Default,
): BduiColor {
    return BduiColor(token = this ?: fallbackColor.token)
}

fun BduiBorderResponse?.toBduiBorder(): BduiBorder? {
    return this?.let { border ->
        BduiBorder(
            color = border.color.toBduiColor(),
            thickness = border.thickness,
        )
    }
}

fun BduiShapeResponse?.toBduiShape(): BduiShape? {
    return this?.let { shape ->
        when (shape.type) {
            BduiShapeType.ROUNDED_CORNERS -> {
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

fun List<BduiInteraction>.toBduiInteractions(): BduiComponentInteractionsUi {
    return BduiComponentInteractionsUi(
        onClick = this.toBduiInteractionActions(
            interactionType = BduiInteractionType.ON_CLICK,
        ),
        onShow = this.toBduiInteractionActions(
            interactionType = BduiInteractionType.ON_SHOW,
        ),
    )
}

fun List<BduiInteraction>.toBduiInteractionActions(
    interactionType: BduiInteractionType,
): List<BduiActionUi>? {
    return this
        .find { it.type == interactionType }
        ?.toBduiInteractionActions()
}

fun BduiInteraction.toBduiInteractionActions(): List<BduiActionUi> {
    return this.actions.map { action ->
        when (action.type) {
            BduiActionType.COMMAND -> {
                BduiActionUi.Command(
                    // TODO перепроверить маппинг, мб сделать валидацию
                    name = action.name.orEmpty(),
                    params = action.params,
                )
            }
            BduiActionType.UPDATE_SCREEN -> {
                BduiActionUi.UpdateScreen(
                    screenName = action.screenName.orEmpty(),
                    screenNavigationParams = action.screenNavigationParams,
                )
            }
        }
    }
}

fun BduiComponentInsets.toComponentInsets(): BduiComponentInsetsUi {
    return BduiComponentInsetsUi(
        start = this.start,
        end = this.end,
        top = this.top,
        bottom = this.bottom,
    )
}

fun BduiComponentSizeResponse.toComponentSize(): BduiComponentSize {
    return when (this.type) {
        BduiComponentSizeType.FIXED -> BduiComponentSize.Fixed(this.value.orZero())
        BduiComponentSizeType.WEIGHTED -> BduiComponentSize.Weighted(this.fraction.orZero())
        BduiComponentSizeType.MATCH_PARENT -> BduiComponentSize.MatchParent
        BduiComponentSizeType.WRAP_CONTENT -> BduiComponentSize.WrapContent
    }
}
