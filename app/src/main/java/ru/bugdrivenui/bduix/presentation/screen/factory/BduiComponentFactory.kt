package ru.bugdrivenui.bduix.presentation.screen.factory

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import ru.bugdrivenui.bduix.data.model.BduiActionType
import ru.bugdrivenui.bduix.data.model.BduiComponentResponse
import ru.bugdrivenui.bduix.data.model.BduiComponentType
import ru.bugdrivenui.bduix.data.model.BduiInteraction
import ru.bugdrivenui.bduix.data.model.BduiInteractionType
import ru.bugdrivenui.bduix.presentation.screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.screen.model.BduiColor
import ru.bugdrivenui.bduix.presentation.screen.model.BduiComponentInteractionsUi
import ru.bugdrivenui.bduix.presentation.screen.model.BduiComponentUi
import javax.inject.Inject

class BduiComponentFactory @Inject constructor() {

    fun create(
        component: BduiComponentResponse,
    ): BduiComponentUi {
        return when (component.type) {
            BduiComponentType.TEXT -> createTextComponent(component)
            BduiComponentType.IMAGE -> TODO()
            BduiComponentType.BUTTON -> TODO()
            BduiComponentType.INPUT -> TODO()
            BduiComponentType.ROW -> TODO()
            BduiComponentType.COLUMN -> createColumnComponent(component)
            BduiComponentType.BOX -> TODO()
        }
    }

    private fun createTextComponent(
        component: BduiComponentResponse,
    ): BduiComponentUi {
        return BduiComponentUi.Text(
            id = component.id,
            hash = component.hash,
            interactions = component.interactions?.let(::createInteractions),
            text = component.text.orEmpty(),
            textColor = component.color
                ?.let { BduiColor(Color(it.toColorInt())) }
                ?: BduiColor.Default
        )
    }

    private fun createColumnComponent(
        component: BduiComponentResponse,
    ): BduiComponentUi {
        return BduiComponentUi.Column(
            id = component.id,
            hash = component.hash,
            interactions = component.interactions?.let(::createInteractions),
            children = component.children?.map(::create).orEmpty(),
        )
    }

    private fun createInteractions(
        interactions: List<BduiInteraction>,
    ): BduiComponentInteractionsUi {
        return BduiComponentInteractionsUi(
            onClick = createActionsForInteraction(
                interactions = interactions,
                interactionType = BduiInteractionType.ON_CLICK,
            ),
            onShow = createActionsForInteraction(
                interactions = interactions,
                interactionType = BduiInteractionType.ON_SHOW,
            )
        )
    }

    private fun createActionsForInteraction(
        interactions: List<BduiInteraction>,
        interactionType: BduiInteractionType,
    ): List<BduiActionUi>? {
        return interactions
            .find { it.type == interactionType }
            ?.let(::createInteractionActions)
    }

    private fun createInteractionActions(
        interaction: BduiInteraction,
    ): List<BduiActionUi> {
        return interaction.actions.map { action ->
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
}