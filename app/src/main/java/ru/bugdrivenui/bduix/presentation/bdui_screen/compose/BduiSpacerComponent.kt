package ru.bugdrivenui.bduix.presentation.bdui_screen.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.applyHeightSize
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.applyWidthSize
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi

@Composable
fun BduiSpacerComponent(
    modifier: Modifier = Modifier,
    component: BduiComponentUi.Spacer,
) {
    Spacer(
        modifier = modifier
            .applyWidthSize(component.width)
            .applyHeightSize(component.height)
    )
}