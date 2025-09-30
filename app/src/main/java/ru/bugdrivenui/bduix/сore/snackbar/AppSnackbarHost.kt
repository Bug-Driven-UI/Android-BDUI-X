package ru.bugdrivenui.bduix.сore.snackbar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collectLatest
import ru.bugdrivenui.bduix.presentation.ui.theme.BduiTheme
import ru.bugdrivenui.bduix.presentation.ui.theme.ManropeFont

@Composable
fun AppSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier,
    ) { data ->
        Snackbar(
            modifier = Modifier.padding(horizontal = 8.dp),
            shape = RoundedCornerShape(28.dp),
            containerColor = BduiTheme.colors.component.toast.default,
            contentColor = BduiTheme.colors.text.inversePrimary,
        ) {
            Text(
                text = data.visuals.message,
                color = BduiTheme.colors.text.inversePrimary,
                style = BduiTheme.typography.M20_P,
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 16.dp,
                )
            )
        }
    }
}

@Composable
fun rememberAppSnackbarHostState(
    snackbarManager: SnackbarManager,
): SnackbarHostState {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(snackbarManager) {
        snackbarManager.messages.collectLatest { message ->
            snackbarHostState.showSnackbar(
                message = message.text,
                duration = message.duration,
            )
        }
    }
    return snackbarHostState
}
