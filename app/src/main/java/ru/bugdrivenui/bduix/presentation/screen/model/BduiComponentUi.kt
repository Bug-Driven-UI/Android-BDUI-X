package ru.bugdrivenui.bduix.presentation.screen.model

import androidx.compose.runtime.Immutable

@Immutable
sealed interface BduiComponentUi {

    val id: String
    val hash: String
    val type: BduiComponentTypeUi
    val interactions: BduiComponentInteractionsUi?
    val paddings: BduiComponentInsetsUi?
    val margins: BduiComponentInsetsUi?
    val width: BduiComponentSize
    val height: BduiComponentSize
    val backgroundColor: BduiColor?
    val border: BduiBorder?
    val shape: BduiShape?

    data class Text(
        override val id: String,
        override val hash: String,
        override val interactions: BduiComponentInteractionsUi?,
        override val paddings: BduiComponentInsetsUi?,
        override val margins: BduiComponentInsetsUi?,
        override val width: BduiComponentSize,
        override val height: BduiComponentSize,
        override val backgroundColor: BduiColor?,
        override val border: BduiBorder?,
        override val shape: BduiShape?,
        val text: BduiText,
    ) : BduiComponentUi {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.TEXT
    }

    data class Image(
        override val id: String,
        override val hash: String,
        override val interactions: BduiComponentInteractionsUi?,
        override val paddings: BduiComponentInsetsUi?,
        override val margins: BduiComponentInsetsUi?,
        override val width: BduiComponentSize,
        override val height: BduiComponentSize,
        override val backgroundColor: BduiColor?,
        override val border: BduiBorder?,
        override val shape: BduiShape?,
        val imageUrl: String,
    ) : BduiComponentUi {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.IMAGE
    }

    data class Button(
        override val id: String,
        override val hash: String,
        override val interactions: BduiComponentInteractionsUi?,
        override val paddings: BduiComponentInsetsUi?,
        override val margins: BduiComponentInsetsUi?,
        override val width: BduiComponentSize,
        override val height: BduiComponentSize,
        override val backgroundColor: BduiColor?,
        override val border: BduiBorder?,
        override val shape: BduiShape?,
        val text: BduiText,
        val enabled: Boolean,
    ) : BduiComponentUi {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.BUTTON
    }

    data class Input(
        override val id: String,
        override val hash: String,
        override val interactions: BduiComponentInteractionsUi?,
        override val paddings: BduiComponentInsetsUi?,
        override val margins: BduiComponentInsetsUi?,
        override val width: BduiComponentSize,
        override val height: BduiComponentSize,
        override val backgroundColor: BduiColor?,
        override val border: BduiBorder?,
        override val shape: BduiShape?,
        val text: BduiText,
        val placeholder: BduiText,
        val hint: BduiText,
    ) : BduiComponentUi {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.INPUT
    }

    @Immutable
    sealed interface Container : BduiComponentUi {

        val children: List<BduiComponentUi>
    }

    data class Column(
        override val id: String,
        override val hash: String,
        override val interactions: BduiComponentInteractionsUi?,
        override val paddings: BduiComponentInsetsUi?,
        override val margins: BduiComponentInsetsUi?,
        override val width: BduiComponentSize,
        override val height: BduiComponentSize,
        override val backgroundColor: BduiColor?,
        override val children: List<BduiComponentUi>,
        override val border: BduiBorder?,
        override val shape: BduiShape?,
    ) : Container {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.COLUMN
    }

    data class Row(
        override val id: String,
        override val hash: String,
        override val interactions: BduiComponentInteractionsUi?,
        override val paddings: BduiComponentInsetsUi?,
        override val margins: BduiComponentInsetsUi?,
        override val width: BduiComponentSize,
        override val height: BduiComponentSize,
        override val backgroundColor: BduiColor?,
        override val children: List<BduiComponentUi>,
        override val border: BduiBorder?,
        override val shape: BduiShape?,
    ) : Container {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.ROW
    }

    data class Box(
        override val id: String,
        override val hash: String,
        override val interactions: BduiComponentInteractionsUi?,
        override val paddings: BduiComponentInsetsUi?,
        override val margins: BduiComponentInsetsUi?,
        override val width: BduiComponentSize,
        override val height: BduiComponentSize,
        override val backgroundColor: BduiColor?,
        override val children: List<BduiComponentUi>,
        override val border: BduiBorder?,
        override val shape: BduiShape?,
    ) : Container {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.BOX
    }
}

data class BduiText(
    val value: String,
    val color: BduiColor,
    val style: BduiTextStyle,
)

data class BduiTextStyle(
    val decorationType: BduiTextDecorationType,
    val weight: Int,
    val size: Int,
)

data class BduiColor(val token: String) {
    companion object {
        val Default = BduiColor("#FF0000")
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

enum class BduiTextDecorationType {
    REGULAR,
    ITALIC,
    UNDERLINE,
    STRIKETHROUGH,
    STRIKETHROUGH_RED,
}
