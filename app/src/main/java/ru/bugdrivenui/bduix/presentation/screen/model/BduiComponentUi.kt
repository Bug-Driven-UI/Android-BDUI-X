package ru.bugdrivenui.bduix.presentation.screen.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

sealed interface BduiComponentUi {

    val id: String
    val hash: String
    val type: BduiComponentTypeUi
    val interactions: BduiComponentInteractionsUi?

    data class Text(
        override val id: String,
        override val hash: String,
        override val interactions: BduiComponentInteractionsUi?,
        val text: String,
        val textColor: BduiColor,
    ) : BduiComponentUi {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.TEXT
    }

    data class Image(
        override val id: String,
        override val hash: String,
        override val interactions: BduiComponentInteractionsUi?,
        val imageUrl: String,
    ) : BduiComponentUi {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.IMAGE
    }

    data class Button(
        override val id: String,
        override val hash: String,
        override val interactions: BduiComponentInteractionsUi?,
        val text: String,
        val textColor: BduiColor,
    ) : BduiComponentUi {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.BUTTON
    }

    data class Input(
        override val id: String,
        override val hash: String,
        override val interactions: BduiComponentInteractionsUi?,
        val text: String,
        val textColor: BduiColor,
        val placeholderText: String,
        val placeholderTextColor: BduiColor,
    ) : BduiComponentUi {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.INPUT
    }

    data class Column(
        override val id: String,
        override val hash: String,
        override val interactions: BduiComponentInteractionsUi?,
    ) : BduiComponentUi {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.COLUMN
    }

    data class Row(
        override val id: String,
        override val hash: String,
        override val interactions: BduiComponentInteractionsUi?,
    ) : BduiComponentUi {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.ROW
    }

    data class Box(
        override val id: String,
        override val hash: String,
        override val interactions: BduiComponentInteractionsUi?,
    ) : BduiComponentUi {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.BOX
    }
}

data class BduiColor(val value: Color) {
    companion object {
        val Default = BduiColor(Color.Red)
    }
}

@Immutable
data class BduiComponentInteractionsUi(
    val onClick: List<BduiActionUi>?,
    val onShow: List<BduiActionUi>?,
)

@Immutable
sealed interface BduiActionUi {

    data class Command(
        val name: String,
        val params: Map<String, String>?,
    ) : BduiActionUi

    data class UpdateScreen(
        val screenName: String,
        val screenNavigationParams: Map<String, String>?,
    ) : BduiActionUi
}

enum class BduiComponentTypeUi {
    TEXT,
    IMAGE,
    BUTTON,
    INPUT,
    ROW,
    COLUMN,
    BOX,
}

enum class BduiInteractionTypeUi {
    ON_CLICK,
    ON_SHOW,
}

enum class BduiActionTypeUi {
    COMMAND,
    UPDATE_SCREEN,
}
