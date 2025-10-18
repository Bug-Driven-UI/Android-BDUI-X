package ru.bugdrivenui.bduix.presentation.bdui_screen.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.bugdrivenui.bduix.presentation.bdui_screen.local_state.rememberTextOrLocalState
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toCompose
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toComposeTextStyle
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiTextDecorationType

@Composable
fun BduiTextComponent(
    component: BduiComponentUi.Text,
    modifier: Modifier,
) {
    val text = rememberTextOrLocalState(component.text.value)

    Text(
        modifier = modifier,
        text = text.value,
        style = component.text.toComposeTextStyle(),
        textAlign = component.text.textAlignment.toCompose(),
    )

    if (component.text.style.decorationType == BduiTextDecorationType.STRIKETHROUGH_RED) {
        // TODO отдельный компонент под RedStrokethroughText
    } else {
        // TODO обычный текст
    }
}