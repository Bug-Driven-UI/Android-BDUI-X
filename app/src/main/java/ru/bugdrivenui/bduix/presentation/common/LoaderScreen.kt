package ru.bugdrivenui.bduix.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.bugdrivenui.bduix.presentation.bdui_screen.compose.BduiLoaderComponent

@Composable
fun LoaderScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        BduiLoaderComponent(
            modifier = Modifier.size(
                width = 24.dp,
                height = 24.dp,
            )
        )
    }
}