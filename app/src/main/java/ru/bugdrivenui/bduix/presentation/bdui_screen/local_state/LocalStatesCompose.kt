package ru.bugdrivenui.bduix.presentation.bdui_screen.local_state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.contentOrNull
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.TextOrLocalState
import ru.bugdrivenui.bduix.utils.emptyString

val LocalLocalStates = compositionLocalOf<StateFlow<LocalStates>> {
    error("LocalLocalStates is not provided")
}

@Composable
fun StateFlow<LocalStates>.collectLocalState(
    path: Path,
    initialValue: String = emptyString(),
): State<String> {
    val flow = remember(this) {
        this
            .map { states -> states[path]?.contentOrNull ?: emptyString() }
            .distinctUntilChanged()
    }
    return flow.collectAsStateWithLifecycle(initialValue)
}

@Composable
fun rememberTextOrLocalState(
    textOrLocalState: TextOrLocalState,
): State<String> {
    val localStatesFlow = LocalLocalStates.current
    return when (val value = textOrLocalState) {
        is TextOrLocalState.Text -> rememberUpdatedState(value.value)
        is TextOrLocalState.LocalState -> localStatesFlow.collectLocalState(value.path)
    }
}
