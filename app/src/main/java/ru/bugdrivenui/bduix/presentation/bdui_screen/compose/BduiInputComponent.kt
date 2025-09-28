package ru.bugdrivenui.bduix.presentation.bdui_screen.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.applyHeightSize
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.applyWidthSize
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.asPaddingValues
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toComposeColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toComposeTextStyle
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiBorder
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentInsetsUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentSize
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiShape
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiText
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiTextDecorationType
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiTextStyle

@Composable
fun BduiInputComponent(
    modifier: Modifier = Modifier,
    component: BduiComponentUi.Input,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    onValueChanged: ((String) -> Unit)? = null,
) {
    val paddings = component.paddings.asPaddingValues()
    var textState by rememberSaveable(component.id, stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(component.text.value))
    }

    BasicTextField(
        value = textState,
        onValueChange = {
            textState = it
            onValueChanged?.invoke(it.text)
        },
        enabled = enabled,
        singleLine = singleLine,
        textStyle = component.text.toComposeTextStyle(),
        visualTransformation = VisualTransformation.None,
        modifier = modifier
            .applyWidthSize(component.width)
            .applyHeightSize(component.height)
            .defaultMinSize(minHeight = 48.dp)
            .padding(
                start = paddings.calculateLeftPadding(LayoutDirection.Ltr),
                top = paddings.calculateTopPadding(),
                end = paddings.calculateRightPadding(LayoutDirection.Rtl),
                bottom = paddings.calculateBottomPadding(),
            ),
        decorationBox = { inner ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(Modifier.weight(1f)) {
                    if (textState.text.isEmpty()) {
                        Text(
                            text = component.placeholder.value,
                            style = component.placeholder.toComposeTextStyle()
                                .copy(
                                    color = component.placeholder.color.toComposeColor()
                                        .copy(alpha = 0.5f)
                                )
                        )
                    }
                    inner()
                }
                if (textState.text.isNotEmpty()) {
                    Text(
                        text = "✕",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(28.dp)
                            .clip(CircleShape)
                            .clickable {
                                textState = TextFieldValue("")
                                onValueChanged?.invoke("")
                            }
                            .wrapContentSize(Alignment.Center),
                        style = component.text.toComposeTextStyle()
                    )
                }
            }
        }
    )
}


@Preview(showBackground = true, backgroundColor = 0xFF141414)
@Composable
private fun BduiInputComponent_Preview() {
    val component = BduiComponentUi.Input(
        id = "preview_input",
        hash = "preview_hash",
        interactions = null,
        paddings = BduiComponentInsetsUi(16, 12, 16, 12),
        margins = null,
        width = BduiComponentSize.Fixed(280),
        height = BduiComponentSize.WrapContent,
        backgroundColor = BduiColor("#1A965EEB"),
        border = BduiBorder(BduiColor("#965EEB"), 2),
        shape = BduiShape.RoundedCorners(12, 12, 12, 12),
        text = BduiText(
            value = "",
            color = BduiColor("#FFFFFF"),
            style = BduiTextStyle(BduiTextDecorationType.REGULAR, 400, 15)
        ),
        placeholder = BduiText(
            value = "Введите текст",
            color = BduiColor("#FFFFFF"),
            style = BduiTextStyle(BduiTextDecorationType.REGULAR, 400, 15)
        ),
        hint = BduiText(
            value = "",
            color = BduiColor("#965EEB"),
            style = BduiTextStyle(BduiTextDecorationType.REGULAR, 400, 12)
        ),
    )
    BduiInputComponent(component = component)
}