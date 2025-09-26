package ru.bugdrivenui.bduix.presentation.screen.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.bugdrivenui.bduix.presentation.common.UiState
import ru.bugdrivenui.bduix.presentation.screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.screen.model.BduiScreenUiModel
import ru.bugdrivenui.bduix.presentation.screen.viewmodel.BduiScreenViewModel
import ru.bugdrivenui.bduix.utils.bduiBaseProperties
import ru.bugdrivenui.bduix.utils.ifNotNull

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
    LazyColumn(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars),
    ) {
        items(model.components) { component ->
            BduiComponent(
                modifier = Modifier
                    .bduiBaseProperties(component)
                    .ifNotNull(component.interactions?.onClick) { onClickActions ->
                        clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(),
                            enabled = (component as? BduiComponentUi.Button)?.enabled ?: true,
                            onClick = { onClickActions.forEach { onAction(it) } },
                        )
                    },
                component = component,
                onAction = onAction,
            )
        }
    }
}
