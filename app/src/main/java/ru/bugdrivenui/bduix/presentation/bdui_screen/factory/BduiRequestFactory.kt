package ru.bugdrivenui.bduix.presentation.bdui_screen.factory

import kotlinx.serialization.json.JsonElement
import ru.bugdrivenui.bduix.data.model.action.ActionRequestModel
import ru.bugdrivenui.bduix.data.model.action.ScreenDoActionRequestModel
import ru.bugdrivenui.bduix.data.model.render.ScreenRenderRequestModel
import ru.bugdrivenui.bduix.presentation.bdui_screen.hash.BduiScreenHashCollector
import ru.bugdrivenui.bduix.presentation.bdui_screen.local_state.LocalStateResolver
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.RenderedScreenUi
import javax.inject.Inject

class BduiRequestFactory @Inject constructor(
    private val localStateResolver: LocalStateResolver,
    private val hashCollector: BduiScreenHashCollector,
) {

    fun createScreenRenderRequest(
        screenName: String,
        screenParams: Map<String, JsonElement>?,
    ): ScreenRenderRequestModel {
        return ScreenRenderRequestModel(
            data = ScreenRenderRequestModel.Data(
                screenName = screenName,
                variables = screenParams ?: emptyMap(),
            ),
        )
    }

    fun createDoActionRequest(
        actions: List<BduiActionUi.Remote>,
        screenData: RenderedScreenUi?,
    ): ScreenDoActionRequestModel {
        return ScreenDoActionRequestModel(
            actions = actions.map { action ->
                when (action) {
                    is BduiActionUi.Command -> {
                        val params = action.params?.mapValues { (_, value) ->
                            localStateResolver.resolveLocalStateRefs(value)
                        }
                        ActionRequestModel.Command(
                            name = action.name,
                            params = params,
                        )
                    }

                    is BduiActionUi.UpdateScreen -> {
                        ActionRequestModel.UpdateScreen(
                            screenName = action.screenName,
                            screenHashes = ActionRequestModel.UpdateScreen.ScreenHashes(
                                hashCollector.collect(
                                    componentTree = screenData?.components ?: emptyList(),
                                ),
                            ),
                            topBarHash = screenData?.scaffold?.topBar?.let { topBar ->
                                ActionRequestModel.UpdateScreen.ScreenPartHashes(
                                    hash = hashCollector.collect(component = topBar),
                                )
                            },
                            bottomBarHash = screenData?.scaffold?.bottomBar?.let { bottomBar ->
                                ActionRequestModel.UpdateScreen.ScreenPartHashes(
                                    hash = hashCollector.collect(component = bottomBar),
                                )
                            },
                            screenNavigationParams = action.screenNavigationParams,
                        )
                    }
                }
            }
        )
    }
}