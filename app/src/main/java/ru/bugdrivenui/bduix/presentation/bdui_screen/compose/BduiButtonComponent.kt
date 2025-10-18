package ru.bugdrivenui.bduix.presentation.bdui_screen.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.utils.bduiBaseProperties

@Composable
fun BduiButtonComponent(
    component: BduiComponentUi.Button,
    onAction: (BduiActionUi) -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        BduiTextComponent(
            component = component.text,
            modifier = Modifier
                // TODO weight RowScope, когда кнопка будет контейнером
                .bduiBaseProperties(
                    component = component.text.baseProperties,
                    onAction = onAction,
                    buttonEnabled = false,
                ),
        )
    }
}

