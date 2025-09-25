package ru.bugdrivenui.bduix.presentation.screen.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.bugdrivenui.bduix.presentation.common.UiState
import ru.bugdrivenui.bduix.presentation.screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.screen.model.BduiScreenUiModel
import ru.bugdrivenui.bduix.presentation.screen.viewmodel.BduiScreenViewModel

@Composable
fun BduiScreen(
    viewModel: BduiScreenViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onAction: (BduiActionUi) -> Unit = remember { viewModel::onAction }

    when (uiState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text("Loading")
            }
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
                model = (uiState as UiState.Content<BduiScreenUiModel>).data,
                onAction = onAction,
            )
        }
    }
}

@Composable
private fun BduiScreenContent(
    model: BduiScreenUiModel,
    onAction: (BduiActionUi) -> Unit,
) {
    LazyColumn {
        items(model.components) { component ->
            BduiComponent(
                component = component,
                onAction = onAction,
            )
        }
    }
}
