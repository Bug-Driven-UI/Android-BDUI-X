package ru.bugdrivenui.bduix.presentation.bdui_screen.model

import androidx.compose.runtime.Immutable
import ru.bugdrivenui.bduix.presentation.utils.PresentationConstants.DEFAULT_BG_COLOR_HEX

data class BduiScaffoldUi(
    val topBar: BduiComponentUi?,
    val bottomBar: BduiComponentUi?,
)

@Immutable
sealed interface BduiComponentUi {

    val baseProperties: BaseProperties
    val type: BduiComponentTypeUi

    data class Text(
        override val baseProperties: BaseProperties,
        val text: BduiText,
    ) : BduiComponentUi {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.TEXT
    }

    data class Image(
        override val baseProperties: BaseProperties,
        val imageUrl: String,
    ) : BduiComponentUi {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.IMAGE
    }

    data class Button(
        override val baseProperties: BaseProperties,
        val text: Text,
        val enabled: Boolean,
    ) : BduiComponentUi {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.BUTTON
    }

    data class Input(
        override val baseProperties: BaseProperties,
        val text: BduiText,
        val placeholder: BduiText?,
        val rightIcon: Image?,
        val onValueChangedActions: List<BduiActionUi.InputValueChangedApplicable>,
    ) : BduiComponentUi {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.INPUT
    }

    data class Spacer(
        override val baseProperties: BaseProperties,
    ) : BduiComponentUi {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.SPACER
    }

    @Immutable
    sealed interface Container : BduiComponentUi {

        val children: List<BduiComponentUi>
    }

    data class Column(
        val verticalArrangement: BduiVerticalArrangement?,
        val horizontalAlignment: BduiHorizontalAlignment?,
        override val baseProperties: BaseProperties,
        override val children: List<BduiComponentUi>,
    ) : Container {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.COLUMN
    }

    data class Row(
        val horizontalArrangement: BduiHorizontalArrangement?,
        val verticalAlignment: BduiVerticalAlignment?,
        val isScrollable: Boolean = false,
        override val baseProperties: BaseProperties,
        override val children: List<BduiComponentUi>,
    ) : Container {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.ROW
    }

    data class Box(
        val contentAlignment: BduiHorizontalAndVerticalAlignment?,
        override val baseProperties: BaseProperties,
        override val children: List<BduiComponentUi>,
    ) : Container {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.BOX
    }

    data class BaseProperties(
        val id: String,
        val hash: String,
        val interactions: BduiComponentInteractionsUi?,
        val paddings: BduiComponentInsetsUi?,
        val margins: BduiComponentInsetsUi?,
        val width: BduiComponentSize,
        val height: BduiComponentSize,
        val backgroundColor: BduiColor?,
        val border: BduiBorder?,
        val shape: BduiShape?,
    )
}

sealed interface TextOrLocalState {

    data class Text(val value: String) : TextOrLocalState
    data class LocalState(val path: String) : TextOrLocalState
}

data class BduiText(
    val value: TextOrLocalState,
    val color: BduiColor,
    val style: BduiTextStyle,
    val textAlignment: BduiTextAlignment?,
)

enum class BduiTextAlignment {
    START,
    CENTER,
    END,
}

data class BduiTextStyle(
    val decorationType: BduiTextDecorationType,
    val weight: Int,
    val size: Int,
)

data class BduiColor(val hex: String) {
    companion object {
        val Default = BduiColor(DEFAULT_BG_COLOR_HEX)
    }
}

data class BduiBorder(
    val color: BduiColor,
    val thickness: Int,
)

sealed interface BduiShape {

    data class RoundedCorners(
        val topStart: Int,
        val topEnd: Int,
        val bottomStart: Int,
        val bottomEnd: Int,
    ) : BduiShape
}

data class BduiComponentInsetsUi(
    val start: Int,
    val end: Int,
    val top: Int,
    val bottom: Int,
)

sealed interface BduiComponentSize {
    data class Fixed(val value: Int) : BduiComponentSize
    data class Weighted(val fraction: Float) : BduiComponentSize
    data object MatchParent : BduiComponentSize
    data object WrapContent : BduiComponentSize
}

@Immutable
data class BduiComponentInteractionsUi(
    val onClick: List<BduiActionUi>?,
    val onShow: List<BduiActionUi>?,
)

enum class BduiComponentTypeUi {
    TEXT,
    IMAGE,
    BUTTON,
    INPUT,
    ROW,
    COLUMN,
    BOX,
    SPACER,
}

enum class BduiTextDecorationType {
    REGULAR,
    ITALIC,
    UNDERLINE,
    STRIKETHROUGH,
    STRIKETHROUGH_RED,
}

fun BduiComponentUi.allNodesCount(): Int = when (this) {
    is BduiComponentUi.Container -> 1 + children.sumOf { it.allNodesCount() }
    else -> 1
}

fun List<BduiComponentUi>.allNodesCount(): Int = sumOf { it.allNodesCount() }
