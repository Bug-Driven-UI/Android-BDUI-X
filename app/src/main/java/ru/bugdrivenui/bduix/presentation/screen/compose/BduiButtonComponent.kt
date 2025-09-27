package ru.bugdrivenui.bduix.presentation.screen.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.bugdrivenui.bduix.presentation.screen.mapper.toComposeColor
import ru.bugdrivenui.bduix.presentation.screen.mapper.toComposeFontWeight
import ru.bugdrivenui.bduix.presentation.screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.ui.theme.ManropeFont
import ru.bugdrivenui.bduix.utils.getEnabledAlpha

@Composable
fun BduiButtonComponent(
    component: BduiComponentUi.Button,
    modifier: Modifier,
) {
    BduiButtonComponent(
        modifier = modifier,
        text = component.text.value,
        textColor = component.text.color.toComposeColor(),
        fontSize = component.text.style.size,
        fontWeight = component.text.style.weight,
        enabled = component.enabled,
    )
}

@Composable
private fun BduiButtonComponent(
    text: String,
    textColor: Color,
    fontSize: Int,
    fontWeight: Int,
    enabled: Boolean,
    modifier: Modifier,
) {
    // onClick задается в interactions наряду с другими компонентами
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier
                .padding(
                    vertical = 16.dp,
                    horizontal = 20.dp,
                ),
            text = text,
            color = textColor.copy(
                alpha = getEnabledAlpha(enabled),
            ),
            fontSize = fontSize.sp,
            fontWeight = fontWeight.toComposeFontWeight(),
            fontFamily = ManropeFont,
        )
    }
}

@Preview
@Composable
private fun BduiButtonComponentPreview() {
    BduiButtonComponent(
        text = "Оформить доставку",
        textColor = Color(0xFFFFFFFF),
        fontSize = 15,
        fontWeight = 600,
        enabled = true,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = Color(0xFF965EEB),
                shape = RoundedCornerShape(16.dp),
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = {},
            ),
    )
}
