package ru.bugdrivenui.bduix.presentation.login_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.bugdrivenui.bduix.R
import ru.bugdrivenui.bduix.presentation.bdui_screen.compose.BduiLoaderComponent
import ru.bugdrivenui.bduix.presentation.bdui_screen.compose.PlainInputField
import ru.bugdrivenui.bduix.presentation.ui.theme.BduiTheme

@Composable
fun LoginScreenWrapper(
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onEvent: (LoginEvent) -> Unit = remember { viewModel::onEvent }

    LoginScreen(
        uiState = uiState,
        onEvent = onEvent,
    )
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = R.string.login_title_text),
            style = BduiTheme.typography.H20_H2,
        )
        Spacer(modifier = Modifier.height(32.dp))

        PlainInputField(
            value = TextFieldValue(uiState.apiKey, selection = TextRange(uiState.apiKey.length)),
            onValueChange = { onEvent.invoke(LoginEvent.OnApiKeyChanged(it.text)) },
            textStyle = BduiTheme.typography.M20_P,
            placeholder = "apiKey",
            enabled = !uiState.isLoading,
            singleLine = true,
            showClearButton = true,
            modifier = Modifier.fillMaxWidth()
        )
        uiState.errorText?.let { error ->
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = error,
                style = BduiTheme.typography.M20_P,
                color = Color(0xFFFF4053),
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    color = BduiTheme.colors.component.button.bg.primary,
                    shape = RoundedCornerShape(16.dp),
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onEvent.invoke(LoginEvent.OnLoginClicked) },
                    enabled = !uiState.isLoading,
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (uiState.isLoading) {
                BduiLoaderComponent(
                    modifier = Modifier
                        .padding(
                            vertical = 16.dp,
                            horizontal = 20.dp,
                        )
                        .size(
                            width = 20.dp,
                            height = 20.dp,
                        ),
                    color = Color.White,
                )
            } else {
                Text(
                    modifier = Modifier
                        .padding(
                            vertical = 16.dp,
                            horizontal = 20.dp,
                        ),
                    text = stringResource(id = R.string.login_button_text),
                    color = BduiTheme.colors.component.button.text.primary,
                    style = BduiTheme.typography.M20_P,
                )
            }
        }
    }
}
