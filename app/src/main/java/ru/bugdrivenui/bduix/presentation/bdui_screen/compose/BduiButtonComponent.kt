package ru.bugdrivenui.bduix.presentation.bdui_screen.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentInsetsUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentSize
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiShape
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiText
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiTextDecorationType
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiTextStyle
import ru.bugdrivenui.bduix.presentation.ui.theme.BduiTheme
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

@Preview
@Composable
private fun BduiButtonComponentPreview() = BduiTheme {
    BduiButtonComponent(
        component = BduiComponentUi.Button(
            baseProperties = BduiComponentUi.BaseProperties(
                id = "id",
                hash = "hash",
                interactions = null,
                paddings = null,
                margins = null,
                width = BduiComponentSize.WrapContent,
                height = BduiComponentSize.WrapContent,
                backgroundColor = BduiColor(hex = "#965EEB"),
                border = null,
                shape = BduiShape.RoundedCorners(
                    topStart = 16,
                    topEnd = 16,
                    bottomStart = 16,
                    bottomEnd = 16,
                ),
            ),
            text = BduiComponentUi.Text(
                baseProperties = BduiComponentUi.BaseProperties(
                    id = "id",
                    hash = "hash",
                    interactions = null,
                    paddings = BduiComponentInsetsUi(
                        start = 18,
                        end = 19,
                        top = 15,
                        bottom = 17,
                    ),
                    margins = null,
                    width = BduiComponentSize.WrapContent,
                    height = BduiComponentSize.WrapContent,
                    backgroundColor = BduiColor(hex = "#965EEB"),
                    border = null,
                    shape = BduiShape.RoundedCorners(
                        topStart = 16,
                        topEnd = 16,
                        bottomStart = 16,
                        bottomEnd = 16,
                    ),
                ),
                text = BduiText(
                    value = "Оформить доставку",
                    color = BduiColor(hex = "#FFFFFF"),
                    style = BduiTextStyle(
                        decorationType = BduiTextDecorationType.REGULAR,
                        weight = 600,
                        size = 15,
                    ),
                ),
            ),
            enabled = true,
        ),
        onAction = {},
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
