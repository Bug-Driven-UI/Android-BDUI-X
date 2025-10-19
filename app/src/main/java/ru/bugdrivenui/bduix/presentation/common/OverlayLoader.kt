package ru.bugdrivenui.bduix.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.bugdrivenui.bduix.presentation.bdui_screen.compose.BduiLoaderComponent

@Composable
fun OverlayLoader(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    shouldFillMaxSize: Boolean = true,
    content: @Composable () -> Unit,
) {
    val rootModifier = if (shouldFillMaxSize) modifier.fillMaxSize() else modifier

    Box(modifier = rootModifier) {
        content()
        AnimatedVisibility(
            visible = isLoading,
            modifier = Modifier
                .matchParentSize(),
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.White.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center,
            ) {
                BduiLoaderComponent(Modifier.size(24.dp))
            }
        }
    }
}