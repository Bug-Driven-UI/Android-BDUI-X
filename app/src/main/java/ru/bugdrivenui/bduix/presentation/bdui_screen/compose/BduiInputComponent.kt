package ru.bugdrivenui.bduix.presentation.bdui_screen.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun PlainInputField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    placeholder: String? = null,
    textStyle: TextStyle = TextStyle.Default,
    placeholderStyle: TextStyle = TextStyle.Default,
    showClearButton: Boolean = true,
    onClear: (() -> Unit)? = null,
    leading: (@Composable (() -> Unit))? = null,
    trailing: (@Composable (() -> Unit))? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val resolvedTextStyle = textStyle.merge(TextStyle.Default)
    val resolvedPlaceholderStyle = placeholderStyle.merge(
        TextStyle(color = placeholderStyle.color.takeOrElse {
            LocalContentColor.current.copy(alpha = 0.5f)
        })
    )

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        singleLine = singleLine,
        textStyle = resolvedTextStyle,
        visualTransformation = visualTransformation,
        modifier = modifier
            .defaultMinSize(minHeight = 48.dp)
            .background(
                color = Color(0xFFF2F1F0),
                shape = RoundedCornerShape(12.dp),
            ),
        decorationBox = { inner ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leading != null) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize(Alignment.Center)
                            .padding(end = 8.dp)
                    ) { leading() }
                }

                Box(modifier = Modifier.weight(1f)) {
                    if (value.text.isEmpty() && !placeholder.isNullOrEmpty()) {
                        Text(
                            text = placeholder,
                            style = resolvedPlaceholderStyle
                        )
                    }
                    inner()
                }

                if (trailing != null) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize(Alignment.Center)
                            .padding(start = 8.dp)
                    ) { trailing() }
                } else if (showClearButton && value.text.isNotEmpty()) {
                    Text(
                        text = "✕",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(28.dp)
                            .clickable(enabled = enabled) {
                                onClear?.invoke() ?: onValueChange(TextFieldValue(""))
                            }
                            .wrapContentSize(Alignment.Center),
                        style = resolvedTextStyle
                    )
                }
            }
        }
    )
}

@Composable
fun BduiInputComponent(
    modifier: Modifier = Modifier,
    component: BduiComponentUi.Input,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    showClearButton: Boolean = true,
    onValueChanged: ((String) -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val externalInitial = component.text.value
    var fieldValue by rememberSaveable(component.baseProperties.id, stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(externalInitial))
    }

    // синхронизация с внешним состоянием
    LaunchedEffect(component.text.value) {
        val external = component.text.value
        if (external != fieldValue.text) {
            fieldValue = TextFieldValue(
                text = external,
                selection = TextRange(external.length)
            )
        }
    }

    PlainInputField(
        modifier = modifier,
        value = fieldValue,
        onValueChange = {
            fieldValue = it
            onValueChanged?.invoke(it.text)
        },
        enabled = enabled,
        singleLine = singleLine,
        showClearButton = showClearButton,
        onClear = {
            fieldValue = TextFieldValue("")
            onValueChanged?.invoke("")
        },
        placeholder = component.placeholder?.value,
        textStyle = component.text.toComposeTextStyle(),
        placeholderStyle = component.placeholder
            ?.toComposeTextStyle()
            ?.copy(color = component.placeholder.color.toComposeColor().copy(alpha = 0.5f))
            ?: TextStyle.Default,
        visualTransformation = visualTransformation
    )
}


@Composable
fun BduiInputField(
    modifier: Modifier = Modifier,
    component: BduiComponentUi.Input,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    showClearButton: Boolean = true,
    onClear: (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    PlainInputField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        singleLine = singleLine,
        showClearButton = showClearButton,
        onClear = onClear,
        placeholder = component.placeholder?.value,
        textStyle = component.text.toComposeTextStyle(),
        placeholderStyle = component.placeholder
            ?.toComposeTextStyle()
            ?.copy(color = component.placeholder.color.toComposeColor().copy(alpha = 0.5f))
            ?: TextStyle.Default,
        visualTransformation = visualTransformation
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF141414)
@Composable
private fun BduiInputComponent_Preview() {
    val component = BduiComponentUi.Input(
        baseProperties = BduiComponentUi.BaseProperties(
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
        ),
        text = BduiText(
            value = "",
            color = BduiColor("#FFFFFF"),
            style = BduiTextStyle(BduiTextDecorationType.REGULAR, 400, 15),
            textAlignment = null,
        ),
        placeholder = BduiText(
            value = "Введите текст",
            color = BduiColor("#FFFFFF"),
            style = BduiTextStyle(BduiTextDecorationType.REGULAR, 400, 15),
            textAlignment = null,
        ),
        hint = BduiText(
            value = "",
            color = BduiColor("#965EEB"),
            style = BduiTextStyle(BduiTextDecorationType.REGULAR, 400, 12),
            textAlignment = null,
        ),
    )
    BduiInputComponent(component = component)
}