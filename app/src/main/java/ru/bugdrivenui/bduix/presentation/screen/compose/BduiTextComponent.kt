package ru.bugdrivenui.bduix.presentation.screen.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.bugdrivenui.bduix.presentation.screen.model.BduiComponentUi

@Composable
fun BduiTextComponent(
    component: BduiComponentUi.Text,
    modifier: Modifier,
) {
    Text(
        modifier = modifier,
        text = component.text,
        color = component.textColor.value,
    )
}
