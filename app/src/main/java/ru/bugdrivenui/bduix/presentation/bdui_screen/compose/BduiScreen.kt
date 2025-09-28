package ru.bugdrivenui.bduix.presentation.bdui_screen.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.RenderedScreenUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.viewmodel.BduiScreenViewModel
import ru.bugdrivenui.bduix.presentation.common.LoaderScreen
import ru.bugdrivenui.bduix.presentation.common.UiState
import ru.bugdrivenui.bduix.utils.bduiBaseProperties

@Composable
fun BduiScreen(
    viewModel: BduiScreenViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onAction: (BduiActionUi) -> Unit = remember { viewModel::onAction }

    BackHandler(
        onBack = { onAction.invoke(BduiActionUi.NavigateBack) },
    )

    when (uiState) {
        is UiState.Loading -> {
            LoaderScreen()
        }
        is UiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text("Error")
            }
        }
        is UiState.Content -> {
            BduiScreenContent(
                model = (uiState as UiState.Content<RenderedScreenUi>).data,
                onAction = onAction,
            )
        }
    }
}

@Composable
private fun BduiScreenContent(
    model: RenderedScreenUi,
    onAction: (BduiActionUi) -> Unit,
) {
    Scaffold(
        topBar = {
            model.scaffold?.topBar?.let { topBar ->
                BduiComponent(
                    modifier = Modifier
                        .statusBarsPadding()
                        .bduiBaseProperties(
                            component = topBar.baseProperties,
                            onAction = onAction,
                            buttonEnabled = (topBar as? BduiComponentUi.Button)?.enabled,
                        ),
                    component = topBar,
                    onAction = onAction,
                )
            }
        },
        bottomBar = {
            model.scaffold?.bottomBar?.let { bottomBar ->
                BduiComponent(
                    modifier = Modifier
                        .systemBarsPadding()
                        .bduiBaseProperties(
                            component = bottomBar.baseProperties,
                            onAction = onAction,
                            buttonEnabled = (bottomBar as? BduiComponentUi.Button)?.enabled,
                        ),
                    component = bottomBar,
                    onAction = onAction,
                )
            }
        }
    ) { contentPadding ->
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
