package ru.bugdrivenui.bduix.presentation.screen.factory

import ru.bugdrivenui.bduix.data.model.BduiComponentResponse
import ru.bugdrivenui.bduix.data.model.BduiComponentType
import ru.bugdrivenui.bduix.data.model.BduiScreenResponse
import ru.bugdrivenui.bduix.presentation.screen.model.BduiComponentState
import ru.bugdrivenui.bduix.presentation.screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.screen.model.BduiScreenUiModel
import ru.bugdrivenui.bduix.utils.emptyString
import javax.inject.Inject

class BduiScreenFactory @Inject constructor(
    private val componentFactory: BduiComponentFactory,
) {

    fun create(
        response: BduiScreenResponse,
    ): BduiScreenUiModel {
        // TODO PersistentMap?
        val componentById = mutableMapOf<String, BduiComponentUi>()
        val childrenIdsById = mutableMapOf<String, List<String>>()
        val stateById = mutableMapOf<String, BduiComponentState>()

        fun traverse(component: BduiComponentResponse, pathId: String) {
            val id = "$pathId/${component.id}"
            componentById[id] = componentFactory.create(
                id = id,
                component = component,
            )

            val childrenIds = component.children?.map { "$id/${it.id}" }.orEmpty()
            childrenIdsById[id] = childrenIds

            stateById[id] = when (component.type) {
                BduiComponentType.TEXT -> {
                    BduiComponentState.Text(
                        text = component.text.orEmpty(),
                    )
                }

                BduiComponentType.BUTTON -> {
                    BduiComponentState.Button(
                        text = component.text.orEmpty(),
                        isEnabled = true,
                    )
                }

                BduiComponentType.INPUT -> {
                    BduiComponentState.Input(
                        text = component.text.orEmpty(),
                        isEnabled = true,
                    )
                }

                else -> {
                    BduiComponentState.Stateless
                }
            }

            component.children?.forEach { component ->
                traverse(component, id)
            }
        }

        response.components.forEach { component ->
            traverse(component, emptyString())
        }

        return BduiScreenUiModel(
            screenId = response.screenId,
            rootComponentsIds = response.components.map { "/${it.id}" },
            componentById = componentById,
            childrenIdsById = childrenIdsById,
            stateById = stateById,
        )
    }
}