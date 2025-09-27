package ru.bugdrivenui.bduix.presentation.bdui_screen.factory

import ru.bugdrivenui.bduix.data.model.BduiScreenResponse
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiScreenUiModel
import javax.inject.Inject

class BduiScreenFactory @Inject constructor(
    private val componentFactory: BduiComponentFactory,
) {

    fun create(
        response: BduiScreenResponse,
    ): BduiScreenUiModel {

        return BduiScreenUiModel(
            screenId = response.screenId,
            components = response.components.map(componentFactory::create),
        )
    }
}