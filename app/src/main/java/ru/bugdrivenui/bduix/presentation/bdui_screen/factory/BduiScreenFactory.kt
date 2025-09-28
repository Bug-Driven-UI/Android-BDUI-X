package ru.bugdrivenui.bduix.presentation.bdui_screen.factory

import ru.bugdrivenui.bduix.data.model.RenderedScreenModel
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiScaffoldUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.RenderedScreenUi
import javax.inject.Inject

class BduiScreenFactory @Inject constructor(
    private val componentFactory: BduiComponentFactory,
) {

    fun create(
        screen: RenderedScreenModel,
    ): RenderedScreenUi {
        return RenderedScreenUi(
            screenName = screen.screenName,
            version = screen.version,
            components = screen.components.map(componentFactory::create),
            scaffold = screen.scaffold?.let { scaffold ->
                BduiScaffoldUi(
                    topBar = scaffold.topBar?.let(componentFactory::create),
                    bottomBar = scaffold.bottomBar?.let(componentFactory::create),
                )
            },
        )
    }
}