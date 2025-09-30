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
            is BduiActionUi.NavigateTo -> onNavigateTo(action.screenName, action.screenNavigationParams)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun startCollectFlow() {
        val request = ScreenRenderRequestModel(
            data = ScreenRenderRequestModel.Data(
                screenName = screenName,
                variables = screenParams,
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
                            data = screenFactory.create(
                                screen = state.data.screen,
                            ),
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
                }

                is State.Success -> {
                    doActionResponse.data.responses.forEach { actionResponse ->
                        when (actionResponse) {
                            is ActionResponseModel.Command -> onCommandResponse(actionResponse.response.data)
                            is ActionResponseModel.UpdateScreen -> onUpdateScreenResponse(actionResponse.response)
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
        }
    }

    private fun onUpdateScreenResponse(
        updateScreenResponse: ActionResponseModel.UpdateScreen.Response,
    ) {
        _uiState.updateIfContent { state ->
            screenFactory.createPatchedScreen(
                screen = state,
                updateScreenResponse = updateScreenResponse,
            )
        }
    }

    private fun onNavigateBack() {
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
    }

    @AssistedFactory
    interface Factory {
        fun create(
            screenName: String,
            screenParams: Map<String, JsonElement>?,
        ): BduiScreenViewModel
    }
}