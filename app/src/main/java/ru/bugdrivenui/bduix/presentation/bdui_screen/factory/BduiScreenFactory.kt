package ru.bugdrivenui.bduix.presentation.bdui_screen.factory

import ru.bugdrivenui.bduix.data.model.RenderedScreenModel
import ru.bugdrivenui.bduix.data.model.action.ActionResponseModel
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiScaffoldUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.RenderedScreenUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.patch.BduiScreenPatchFactory
import ru.bugdrivenui.bduix.presentation.bdui_screen.patch.BduiScreenPatchManager
import ru.bugdrivenui.bduix.utils.asList
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
        val newTopBar = updateScreenResponse.topBar?.let { topBarPatchesData ->
            screenPatchFactory.createPatches(
                updates = topBarPatchesData,
                factory = componentFactory::create,
            )
        }?.let { topBarPatches ->
            componentPatchManager.applyPatchesToRoot(
                rootChildren = screen.scaffold?.topBar?.asList() ?: emptyList(),
                patches = topBarPatches,
            )
        }

        val newBottomBar = updateScreenResponse.bottomBar?.let { bottomBarPatchesData ->
            screenPatchFactory.createPatches(
                updates = bottomBarPatchesData,
                factory = componentFactory::create,
            )
        }?.let { bottomBarPatches ->
            componentPatchManager.applyPatchesToRoot(
                rootChildren = screen.scaffold?.bottomBar?.asList() ?: emptyList(),
                patches = bottomBarPatches,
            )
        }

        val newComponents = screenPatchFactory.createPatches(
            updates = updateScreenResponse.screen,
            factory = componentFactory::create,
        ).let { screenPatches ->
            componentPatchManager.applyPatchesToRoot(
                rootChildren = screen.components,
                patches = screenPatches,
            )
        }

        return screen.copy(
            components = newComponents,
            scaffold = screen.scaffold?.copy(
                topBar = newTopBar?.firstOrNull(),
                bottomBar = newBottomBar?.firstOrNull(),
            ),
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