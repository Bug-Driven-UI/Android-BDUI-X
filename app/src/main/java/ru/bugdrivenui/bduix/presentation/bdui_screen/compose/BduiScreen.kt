package ru.bugdrivenui.bduix.presentation.bdui_screen.compose

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import ru.bugdrivenui.bduix.core.snackbar.AppSnackbarHost
import ru.bugdrivenui.bduix.core.snackbar.rememberAppSnackbarHostState
import ru.bugdrivenui.bduix.presentation.LocalSnackbarManager
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.RenderedScreenUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.viewmodel.BduiScreenViewModel
import ru.bugdrivenui.bduix.presentation.common.ErrorScreen
import ru.bugdrivenui.bduix.presentation.common.LoaderScreen
import ru.bugdrivenui.bduix.presentation.common.OverlayLoader
import ru.bugdrivenui.bduix.presentation.common.UiState
import ru.bugdrivenui.bduix.presentation.utils.bduiBaseProperties
import ru.bugdrivenui.bduix.utils.toComposeShape

@Composable
fun BduiScreen(
    viewModel: BduiScreenViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onAction: (BduiActionUi) -> Unit = remember { viewModel::onAction }

    BackHandler(
        onBack = { onAction.invoke(BduiActionUi.NavigateBack) },
    )

    Crossfade(
        targetState = uiState.key,
    ) { stateKey ->
        when (stateKey) {
            UiState.Key.LOADING -> {
                LoaderScreen()
            }
            UiState.Key.ERROR -> {
                LaunchedEffect(key1 = Unit) {
                    onAction.invoke(BduiActionUi.ErrorScreenShown)
                }
                ErrorScreen(
                    modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
                    onRetry = { onAction.invoke(BduiActionUi.Retry) },
                )
            }
            UiState.Key.CONTENT -> {
                BduiScreenScaffold(
                    model = (uiState as UiState.Content<RenderedScreenUi>).data,
                    onAction = onAction,
                )
            }
        }
    }
}

@Composable
private fun BduiScreenScaffold(
    model: RenderedScreenUi,
    onAction: (BduiActionUi) -> Unit,
) {
    val renderingStartNs = remember { System.nanoTime() }
    var reported by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val snackbarManager = LocalSnackbarManager.current
    val snackbarHostState = rememberAppSnackbarHostState(snackbarManager)

    LaunchedEffect(key1 = Unit) {
        onAction.invoke(BduiActionUi.ScreenShown)
    }

    Scaffold(
        modifier = Modifier.onGloballyPositioned {
            if (!reported) {
                reported = true
                scope.launch {
                    withFrameNanos { now ->
                        val ms = (now - renderingStartNs) / 1_000_000
                        onAction.invoke(
                            BduiActionUi.ScreenRendered(
                                renderTimeMs = ms,
                                screenVersion = model.version,
                                components = model.components,
                            )
                        )
                    }
                }
            }
        },
        snackbarHost = { AppSnackbarHost(snackbarHostState) },
        topBar = {
            model.scaffold?.topBar?.let { topBar ->
                TopBar(
                    component = topBar,
                    onAction = onAction,
                )
            }
        },
        bottomBar = {
            model.scaffold?.bottomBar?.let { bottomBar ->
                BottomBar(
                    component = bottomBar,
                    onAction = onAction,
                )
            }
        }
    ) { contentPadding ->
        BduiScreenContent(
            model = model,
            onAction = onAction,
            contentPadding = contentPadding,
        )
    }
}

@Composable
private fun BduiScreenContent(
    model: RenderedScreenUi,
    onAction: (BduiActionUi) -> Unit,
    contentPadding: PaddingValues,
) {
    OverlayLoader(isLoading = model.isLoading) {
        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .consumeWindowInsets(contentPadding),
            contentPadding = contentPadding,
        ) {
            items(model.components) { component ->
                BduiComponent(
                    modifier = Modifier
                        .bduiBaseProperties(
                            component = component.baseProperties,
                            onAction = onAction,
                            buttonEnabled = (component as? BduiComponentUi.Button)?.enabled
                        ),
                    component = component,
                    onAction = onAction,
                )
            }
        }
    }
}

@Composable
private fun TopBar(
    component: BduiComponentUi,
    onAction: (BduiActionUi) -> Unit,
) {
    BduiComponent(
        modifier = Modifier
            .statusBarsPadding()
            .bduiBaseProperties(
                component = component.baseProperties,
                onAction = onAction,
                buttonEnabled = (component as? BduiComponentUi.Button)?.enabled,
            ),
        component = component,
        onAction = onAction,
    )
}

@Composable
private fun BottomBar(
    component: BduiComponentUi,
    onAction: (BduiActionUi) -> Unit,
) {
    BduiComponent(
        modifier = Modifier
            .statusBarsPadding()
            .shadow(
                elevation = 8.dp,
                shape = component.baseProperties.shape.toComposeShape(),
            )
            .bduiBaseProperties(
                component = component.baseProperties,
                onAction = onAction,
                buttonEnabled = (component as? BduiComponentUi.Button)?.enabled,
            ),
        component = component,
        onAction = onAction,
    )
}
