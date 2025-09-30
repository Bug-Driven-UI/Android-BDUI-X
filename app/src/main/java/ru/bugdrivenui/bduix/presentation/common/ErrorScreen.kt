package ru.bugdrivenui.bduix.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.bugdrivenui.bduix.R
import ru.bugdrivenui.bduix.presentation.ui.theme.BduiTheme
import ru.bugdrivenui.bduix.presentation.utils.VerticalSpacer

@Composable
fun ErrorScreen(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    Spacer(modifier = Modifier.weight(1f))
    Image(
        modifier = Modifier.size(
            width = 109.dp,
            height = 180.dp,
        ),
        painter = painterResource(id = R.drawable.unknown_error),
        contentDescription = null,
    )
    16.dp.VerticalSpacer()
    Text(
        modifier = Modifier.padding(horizontal = 24.dp),
        text = stringResource(id = R.string.general_error_title),
        color = BduiTheme.colors.text.primary,
        style = BduiTheme.typography.H20_H2,
        textAlign = TextAlign.Center,
    )
    6.dp.VerticalSpacer()
    Text(
        modifier = Modifier.padding(horizontal = 24.dp),
        text = stringResource(id = R.string.general_error_description),
        color = BduiTheme.colors.text.primary,
        style = BduiTheme.typography.M10_P,
        textAlign = TextAlign.Center,
    )
    Spacer(modifier = Modifier.weight(1f))
    RetryButton(onRetry)
}

@Composable
private fun RetryButton(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 10.dp,
                end = 10.dp,
                top = 10.dp,
                bottom = 16.dp,
            )
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = BduiTheme.colors.component.button.bg.primary,
                shape = RoundedCornerShape(16.dp),
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier
                .padding(
                    vertical = 16.dp,
                    horizontal = 20.dp,
                ),
            text = stringResource(id = R.string.general_error_button_text),
            color = BduiTheme.colors.component.button.text.primary,
            style = BduiTheme.typography.M20_P,
        )
    }
}
