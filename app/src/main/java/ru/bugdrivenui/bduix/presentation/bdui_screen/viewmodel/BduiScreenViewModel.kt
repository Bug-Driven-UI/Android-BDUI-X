package ru.bugdrivenui.bduix.presentation.bdui_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonElement
import ru.bugdrivenui.bduix.R
import ru.bugdrivenui.bduix.core.analytics.AnalyticsNavigationMethod
import ru.bugdrivenui.bduix.core.analytics.IAnalyticsLoggerFacade
import ru.bugdrivenui.bduix.data.model.action.ActionRequestModel
import ru.bugdrivenui.bduix.data.model.action.ActionResponseModel
import ru.bugdrivenui.bduix.data.model.action.ScreenDoActionRequestModel
import ru.bugdrivenui.bduix.data.model.render.ScreenRenderRequestModel
import ru.bugdrivenui.bduix.domain.interactor.BduiInteractor
import ru.bugdrivenui.bduix.domain.state.State
import ru.bugdrivenui.bduix.presentation.bdui_screen.factory.BduiScreenFactory
import ru.bugdrivenui.bduix.presentation.bdui_screen.hash.BduiScreenHashCollector
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.RenderedScreenUi
import ru.bugdrivenui.bduix.presentation.common.UiState
import ru.bugdrivenui.bduix.presentation.common.updateIfContent
import ru.bugdrivenui.bduix.core.navigation.NavigationManager
import ru.bugdrivenui.bduix.core.navigation.NavigationRoute
import ru.bugdrivenui.bduix.core.resources.IResourcesWrapper
import ru.bugdrivenui.bduix.core.snackbar.SnackbarManager
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.allNodesCount

@HiltViewModel(assistedFactory = BduiScreenViewModel.Factory::class)
class BduiScreenViewModel @AssistedInject constructor(
    @Assisted private val screenName: String,
    @Assisted private val screenParams: Map<String, JsonElement>?,
    private val bduiInteractor: BduiInteractor,
    private val screenFactory: BduiScreenFactory,
    private val hashCollector: BduiScreenHashCollector,
    private val navigationManager: NavigationManager,
    private val snackbarManager: SnackbarManager,
    private val resourcesWrapper: IResourcesWrapper,
    private val analytics: IAnalyticsLoggerFacade,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<RenderedScreenUi>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _refreshTrigger = MutableSharedFlow<Unit>(
        replay = 1,
        extraBufferCapacity = 1,
    ).also { it.tryEmit(Unit) }

    init {
        startCollectFlow()
    }

    fun onAction(action: BduiActionUi) {
        when (action) {
            BduiActionUi.NavigateBack -> onNavigateBack()
            is BduiActionUi.SendRemoteActions -> onRemoteActions(action.actions)
            BduiActionUi.Retry -> onRetry()
            is BduiActionUi.NavigateTo -> onNavigateTo(
                action.screenName,
                action.screenNavigationParams
            )
            is BduiActionUi.ComponentClicked -> onComponentClicked(action.componentId)
            BduiActionUi.ScreenShown -> onScreenShown()
            BduiActionUi.ErrorScreenShown -> onErrorScreenShown()
            is BduiActionUi.ScreenRendered -> onScreenRendered(
                renderTimeMs = action.renderTimeMs,
                screenVersion = action.screenVersion,
                components = action.components,
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun startCollectFlow() {
        val request = ScreenRenderRequestModel(
            data = ScreenRenderRequestModel.Data(
                screenName = screenName,
                variables = screenParams ?: emptyMap(),
            ),
        )

        _refreshTrigger
            .flatMapLatest {
                bduiInteractor.getScreen(request)
            }
            .map { state ->
                when (state) {
                    State.Loading -> {
                        UiState.Loading
                    }

                    is State.Error -> {
                        UiState.Error
                    }

                    is State.Success -> {
                        UiState.Content(
                            data = screenFactory.create(screen = state.data.screen),
                        )
                    }
                }
            }
            .onEach { state ->
                _uiState.update { state }
            }
            .launchIn(viewModelScope)
    }

    private fun onRemoteActions(
        actions: List<BduiActionUi.Remote>,
    ) = viewModelScope.launch {
        bduiInteractor.doAction(
            request = ScreenDoActionRequestModel(
                actions = actions.map { action ->
                    when (action) {
                        is BduiActionUi.Command -> {
                            ActionRequestModel.Command(
                                name = action.name,
                                params = action.params,
                            )
                        }

                        is BduiActionUi.UpdateScreen -> {
                            ActionRequestModel.UpdateScreen(
                                screenName = action.screenName,
                                hashes = hashCollector.collect(
                                    componentTree = (_uiState.value as? UiState.Content<RenderedScreenUi>)
                                        ?.data
                                        ?.components
                                        ?: emptyList(),
                                ),
                                screenNavigationParams = action.screenNavigationParams,
                            )
                        }
                    }
                }
            )
        ).collectLatest { doActionResponse ->
            when (doActionResponse) {
                State.Loading -> {
                    _uiState.updateIfContent { state ->
                        screenFactory.setLoadingScreen(
                            screen = state,
                            isLoading = true,
                        )
                    }
                }

                is State.Error -> {
                    _uiState.updateIfContent { state ->
                        screenFactory.setLoadingScreen(
                            screen = state,
                            isLoading = false,
                        )
                    }
                    snackbarManager.show(
                        text = resourcesWrapper.getString(R.string.general_error_snackbar_text),
                    )
                    analytics.logErrorSnackbarShown(
                        screenName = screenName,
                        message = null,
                    )
                }

                is State.Success -> {
                    doActionResponse.data.responses.forEach { actionResponse ->
                        when (actionResponse) {
                            is ActionResponseModel.Command -> onCommandResponse(actionResponse.response.data)
                            is ActionResponseModel.UpdateScreen -> onUpdateScreenResponse(
                                actionResponse.response
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onCommandResponse(
        commandResponseData: ActionResponseModel.Command.Response.Data,
    ) {
        _uiState.updateIfContent { state ->
            screenFactory.setLoadingScreen(
                screen = state,
                isLoading = false,
            )
        }
        commandResponseData.fallbackMessage?.let { fallbackMessage ->
            snackbarManager.show(fallbackMessage)
            analytics.logErrorSnackbarShown(
                screenName = screenName,
                message = fallbackMessage,
            )
        }
    }

    private fun onUpdateScreenResponse(
        updateScreenResponse: ActionResponseModel.UpdateScreen.Response,
    ) {
        _uiState.updateIfContent { state ->
            val started = System.nanoTime()
            val patchedScreen = screenFactory.createPatchedScreen(
                screen = state,
                updateScreenResponse = updateScreenResponse,
            )
            val durationMs = (System.nanoTime() - started) / 1_000_000

            analytics.logScreenUpdated(
                screenName = screenName,
                updatedComponentsCount = updateScreenResponse.data.size,
                updateDurationMs = durationMs,
            )

            patchedScreen
        }
    }

    private fun onNavigateBack() {
        analytics.logUserNavigated(
            fromScreenName = screenName,
            toScreenName = null,
            method = AnalyticsNavigationMethod.BACK,
        )
        navigationManager.back()
    }

    private fun onRetry() {
        _refreshTrigger.tryEmit(Unit)
    }

    private fun onNavigateTo(
        screenName: String,
        screenNavigationParams: Map<String, JsonElement>?,
    ) {
        navigationManager.navigate(
            route = NavigationRoute.BduiScreen(
                args = NavigationRoute.BduiScreen.Args(
                    screenName = screenName,
                    screenParams = screenNavigationParams,
                )
            )
        )
        analytics.logUserNavigated(
            fromScreenName = this.screenName,
            toScreenName = screenName,
            method = AnalyticsNavigationMethod.NAVIGATE_TO,
        )
    }

    private fun onComponentClicked(componentId: String) {
        analytics.logComponentClicked(
            screenName = screenName,
            componentId = componentId,
        )
    }

    private fun onScreenShown() {
        analytics.logScreenShown(
            screenName = screenName,
        )
    }

    private fun onErrorScreenShown() {
        analytics.logErrorScreenShown(
            screenName = screenName,
        )
    }

    private fun onScreenRendered(
        renderTimeMs: Long,
        screenVersion: Int,
        components: List<BduiComponentUi>,
    ) {
        analytics.logScreenRendered(
            screenName = screenName,
            screenVersion = screenVersion,
            componentsCount = components.allNodesCount(),
            renderTimeMs = renderTimeMs,
        )
    }

    @AssistedFactory
    interface Factory {
        fun create(
            screenName: String,
            screenParams: Map<String, JsonElement>?,
        ): BduiScreenViewModel
    }
}