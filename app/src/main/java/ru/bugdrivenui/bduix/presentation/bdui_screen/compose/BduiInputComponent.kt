package ru.bugdrivenui.bduix.presentation.bdui_screen.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.bugdrivenui.bduix.presentation.bdui_screen.local_state.rememberTextOrLocalState
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toComposeTextStyle
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.utils.bduiBaseProperties

@Composable
fun BduiInputComponent(
    component: BduiComponentUi.Input,
    onAction: (BduiActionUi) -> Unit,
    modifier: Modifier,
) {
    val text = rememberTextOrLocalState(component.text.value)
    val placeholder = component.placeholder?.value?.let { rememberTextOrLocalState(it) }
    val hint = component.hint?.value?.let { rememberTextOrLocalState(it) }

    BduiInputBasicField(
        modifier = modifier,
        value = text.value,
        onValueChange = { newValue ->
            onAction.invoke(
                BduiActionUi.InputValueChanged(
                    actions = component.onValueChangedActions,
                    newInputValue = newValue,
                )
            )
        },
        placeholder = placeholder?.value,
        textStyle = component.text.toComposeTextStyle(),
        placeholderTextStyle = component.placeholder?.toComposeTextStyle(),
        hint = hint?.value,
        hintTextStyle = component.hint?.toComposeTextStyle(),
        rightIcon = component.rightIcon?.let { iconComponent ->
            {
                BduiImageComponent(
                    modifier = Modifier
                        .bduiBaseProperties(
                            component = iconComponent.baseProperties,
                            onAction = onAction,
                            buttonEnabled = false,
                        ),
                    component = iconComponent,
                )
            }
        },
    )
}

@Composable
fun BduiInputBasicField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    placeholder: String? = null,
    placeholderTextStyle: TextStyle? = null,
    hint: String? = null,
    hintTextStyle: TextStyle? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    rightIcon: @Composable (() -> Unit)? = null,
) {
    Column {
        BasicTextField(
            modifier = modifier.defaultMinSize(minHeight = 44.dp),
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            singleLine = singleLine,
            textStyle = textStyle ?: TextStyle.Default,
            visualTransformation = visualTransformation,
            decorationBox = { inner ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty() && placeholder != null) {
                            Text(
                                text = placeholder,
                                style = placeholderTextStyle ?: TextStyle.Default,
                            )
                        }
                        inner()
                    }
                    rightIcon?.let { icon ->
                        Box(
                            modifier = Modifier.padding(start = 4.dp),
                        ) {
                            icon.invoke()
                        }
                    }
                }
            }
        )
        hint?.let { hint ->
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = hint,
                style = hintTextStyle ?: TextStyle.Default,
            )
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}
