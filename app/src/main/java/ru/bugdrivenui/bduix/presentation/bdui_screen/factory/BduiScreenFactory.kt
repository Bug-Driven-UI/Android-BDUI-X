package ru.bugdrivenui.bduix.presentation.bdui_screen.factory

import ru.bugdrivenui.bduix.data.model.RenderedScreenModel
import ru.bugdrivenui.bduix.data.model.action.ActionResponseModel
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiScaffoldUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.RenderedScreenUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.patch.BduiScreenPatchFactory
import ru.bugdrivenui.bduix.presentation.bdui_screen.patch.BduiScreenPatchManager
import javax.inject.Inject

class BduiScreenFactory @Inject constructor(
    private val componentFactory: BduiComponentFactory,
    private val screenPatchFactory: BduiScreenPatchFactory,
    private val componentPatchManager: BduiScreenPatchManager,
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

    fun createPatchedScreen(
        screen: RenderedScreenUi,
        updateScreenResponse: ActionResponseModel.UpdateScreen.Response,
    ): RenderedScreenUi {
        val patches = screenPatchFactory.createPatches(
            updates = updateScreenResponse.data,
            factory = componentFactory::create,
        )
        val newChildren = componentPatchManager.applyPatchesToRoot(
            rootChildren = screen.components,
            patches = patches,
        )
        return screen.copy(
            components = newChildren,
            isLoading = false,
        )
    }

    fun setLoadingScreen(
        screen: RenderedScreenUi,
        isLoading: Boolean,
    ): RenderedScreenUi {
        return screen.copy(isLoading = isLoading)
    }
}