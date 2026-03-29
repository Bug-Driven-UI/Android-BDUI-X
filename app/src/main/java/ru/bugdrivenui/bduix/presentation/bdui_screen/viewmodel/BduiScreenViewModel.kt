package ru.bugdrivenui.bduix.presentation.bdui_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
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
import kotlinx.serialization.json.JsonPrimitive
import ru.bugdrivenui.bduix.R
import ru.bugdrivenui.bduix.core.analytics.AnalyticsNavigationMethod
import ru.bugdrivenui.bduix.core.analytics.IAnalyticsLoggerFacade
import ru.bugdrivenui.bduix.core.navigation.NavigationManager
import ru.bugdrivenui.bduix.core.navigation.NavigationRoute
import ru.bugdrivenui.bduix.core.navigation.SHOULD_UPDATE_SCREEN_KEY
import ru.bugdrivenui.bduix.core.resources.IResourcesWrapper
import ru.bugdrivenui.bduix.core.snackbar.SnackbarManager
import ru.bugdrivenui.bduix.data.model.RenderedScreenModel
import ru.bugdrivenui.bduix.data.model.RenderedScreenResponseModel
import ru.bugdrivenui.bduix.data.model.action.ActionResponseModel
import ru.bugdrivenui.bduix.data.model.action.ScreenDoActionResponseModel
import ru.bugdrivenui.bduix.domain.interactor.BduiInteractor
import ru.bugdrivenui.bduix.domain.state.State
import ru.bugdrivenui.bduix.presentation.bdui_screen.factory.BduiRequestFactory
import ru.bugdrivenui.bduix.presentation.bdui_screen.factory.BduiScreenFactory
import ru.bugdrivenui.bduix.presentation.bdui_screen.local_state.LocalStateStore
import ru.bugdrivenui.bduix.presentation.bdui_screen.local_state.Path
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.RenderedScreenUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.allNodesCount
import ru.bugdrivenui.bduix.presentation.common.UiState
import ru.bugdrivenui.bduix.presentation.common.updateIfContent
import ru.bugdrivenui.bduix.utils.asList

@HiltViewModel(assistedFactory = BduiScreenViewModel.Factory::class)
class BduiScreenViewModel @AssistedInject constructor(
    @Assisted private val screenName: String,
    @Assisted private val screenParams: Map<String, JsonElement>?,
    private val bduiInteractor: BduiInteractor,
    private val screenFactory: BduiScreenFactory,
    private val navigationManager: NavigationManager,
    private val snackbarManager: SnackbarManager,
    private val resourcesWrapper: IResourcesWrapper,
    private val analytics: IAnalyticsLoggerFacade,
    private val localStateStore: LocalStateStore,
    private val requestFactory: BduiRequestFactory,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<RenderedScreenUi>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    val localStates = localStateStore.localStates

    private var remoteCommandsJob: Job? = null

    private val _refreshTrigger = MutableSharedFlow<Unit>(
        replay = 1,
        extraBufferCapacity = 1,
    ).also { it.tryEmit(Unit) }

    init {
        startCollectFlow()
    }

    fun onAction(action: BduiActionUi) {
        when (action) {
            is BduiActionUi.NavigateBack -> onNavigateBack(action.updatePreviousScreen)
            is BduiActionUi.SendRemoteActions -> onRemoteActions(action.actions)
            BduiActionUi.Retry -> onRetry()
            is BduiActionUi.NavigateTo -> onNavigateTo(
                screenName = action.screenName,
                screenNavigationParams = action.screenNavigationParams,
                toBottomSheet = action.toBottomSheet,
            )

            is BduiActionUi.ComponentClicked -> onComponentClicked(action.componentId)
            BduiActionUi.ScreenShown -> onScreenShown()
            BduiActionUi.ErrorScreenShown -> onErrorScreenShown()
            is BduiActionUi.ScreenRendered -> onScreenRendered(
                renderTimeMs = action.renderTimeMs,
                screenVersion = action.screenVersion,
                components = action.components,
            )
            is BduiActionUi.InputValueChanged -> onInputValueChanged(
                actions = action.actions,
                newInputValue = action.newInputValue,
            )

            is BduiActionUi.SetLocalState -> onSetLocalState(
                path = action.targetPath,
                newValue = action.newValue,
            )

            BduiActionUi.UpdateScreenResultReceived -> onUpdateScreenResultReceived()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun startCollectFlow() {
        _refreshTrigger
            .flatMapLatest {
                bduiInteractor.getScreen(
                    request = requestFactory.createScreenRenderRequest(
                        screenName = screenName,
                        screenParams = screenParams,
                    )
                )
            }
            .onEach(::initializeLocalStateStore)
            .map(::mapToUiState)
            .onEach(::updateUiState)
            .launchIn(viewModelScope)
    }

    private fun initializeLocalStateStore(state: State<RenderedScreenResponseModel>) {
        if (state is State.Success) {
            initializeLocalStateStore(state.data.screen)
        }
    }

    private fun mapToUiState(state: State<RenderedScreenResponseModel>): UiState<RenderedScreenUi> {
        return when (state) {
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

    private fun updateUiState(state: UiState<RenderedScreenUi>) {
        _uiState.update { state }
        if (state is UiState.Error) {
            localStateStore.clear()
        }
    }

    private fun initializeLocalStateStore(screen: RenderedScreenModel) {
        localStateStore.setAll(screen.localStates ?: emptyMap())
    }

    private fun onRemoteActions(
        actions: List<BduiActionUi.Remote>,
    ) {
        remoteCommandsJob = viewModelScope.launch {
            bduiInteractor.doAction(
                request = requestFactory.createDoActionRequest(
                    actions = actions,
                    screenData = (_uiState.value as? UiState.Content<RenderedScreenUi>)?.data,
                ),
            ).collectLatest(::onDoActionResponse)
        }
    }

    private fun onDoActionResponse(response: State<ScreenDoActionResponseModel>) {
        when (response) {
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
                response.data.responses.forEach { actionResponse ->
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
                updatedComponentsCount = updateScreenResponse.screen.size,
                updateDurationMs = durationMs,
            )

            patchedScreen
        }
    }

    private fun onNavigateBack(updatePreviousScreen: Boolean) = viewModelScope.launch {
        if (remoteCommandsJob?.isActive == true) {
            remoteCommandsJob?.join()
        }
        analytics.logUserNavigated(
            fromScreenName = screenName,
            toScreenName = null,
            method = AnalyticsNavigationMethod.BACK,
        )
        if (updatePreviousScreen) {
            navigationManager.backWithResult(SHOULD_UPDATE_SCREEN_KEY, true)
        } else {
            navigationManager.back()
        }
    }

    private fun onRetry() {
        _refreshTrigger.tryEmit(Unit)
    }

    private fun onNavigateTo(
        screenName: String,
        screenNavigationParams: Map<String, JsonElement>?,
        toBottomSheet: Boolean = false,
    ) {
        if (toBottomSheet) {
            navigationManager.navigateToBottomSheet(
                route = NavigationRoute.BottomSheet.BduiBottomSheet,
                args = NavigationRoute.BottomSheet.BduiBottomSheet.Args(
                    screenName = screenName,
                    screenParams = screenNavigationParams,
                ),
            )
        } else {
            navigationManager.navigate(
                route = NavigationRoute.BduiScreen(
                    args = NavigationRoute.BduiScreen.Args(
                        screenName = screenName,
                        screenParams = screenNavigationParams,
                    )
                )
            )
        }
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

    private fun onInputValueChanged(
        actions: List<BduiActionUi.InputValueChangedApplicable>,
        newInputValue: String,
    ) {
        actions.forEach { action ->
            when (action) {
                is BduiActionUi.SendRemoteActions -> onRemoteActions(action.actions)
                is BduiActionUi.SetLocalStateFromInput -> onSetLocalStateFromInput(
                    path = action.targetPath,
                    newInputValue = newInputValue,
                )
            }
        }
    }

    private fun onSetLocalState(
        path: Path,
        newValue: JsonPrimitive,
    ) {
        localStateStore.set(path, newValue)
    }

    private fun onSetLocalStateFromInput(
        path: Path,
        newInputValue: String,
    ) {
        localStateStore.setString(path, newInputValue)
    }

    private fun onUpdateScreenResultReceived() {
        onRemoteActions(
            BduiActionUi.UpdateScreen(
                screenName = screenName,
                screenNavigationParams = screenParams,
            ).asList()
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