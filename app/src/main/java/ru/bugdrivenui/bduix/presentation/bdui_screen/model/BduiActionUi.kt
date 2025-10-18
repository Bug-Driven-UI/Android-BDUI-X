package ru.bugdrivenui.bduix.presentation.bdui_screen.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import ru.bugdrivenui.bduix.presentation.bdui_screen.local_state.Path

@Immutable
sealed interface BduiActionUi {

    sealed interface Remote

    sealed interface InputValueChangedApplicable

    data class Command(
        val name: String,
        val params: Map<String, JsonElement>?,
    ) : Remote

    data class UpdateScreen(
        val screenName: String,
        val screenNavigationParams: Map<String, JsonElement>?,
    ) : Remote

    data class SendRemoteActions(
        val actions: List<Remote>,
    ) : BduiActionUi, InputValueChangedApplicable

    data class NavigateTo(
        val screenName: String,
        val screenNavigationParams: Map<String, JsonElement>?,
        val toBottomSheet: Boolean = false,
    ) : BduiActionUi

    data object ScreenShown : BduiActionUi

    data class ScreenRendered(
        val renderTimeMs: Long,
        val screenVersion: Int,
        val components: List<BduiComponentUi>,
    ) : BduiActionUi

    data object ErrorScreenShown : BduiActionUi

    data class ComponentClicked(
        val componentId: String,
    ) : BduiActionUi

    data class NavigateBack(
        val updatePreviousScreen: Boolean = false,
    ) : BduiActionUi

    data object Retry : BduiActionUi

    data class InputValueChanged(
        val actions: List<InputValueChangedApplicable>,
        val newInputValue: String,
    ) : BduiActionUi

    data class SetLocalStateFromInput(
        val targetPath: Path,
    ) : InputValueChangedApplicable

    data class SetLocalState(
        val targetPath: Path,
        val newValue: JsonPrimitive,
    ) : BduiActionUi

    data object UpdateScreenResultReceived : BduiActionUi
}