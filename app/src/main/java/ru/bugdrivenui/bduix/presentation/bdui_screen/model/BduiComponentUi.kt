package ru.bugdrivenui.bduix.presentation.bdui_screen.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.json.JsonElement
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
        val text: BduiText,
        val enabled: Boolean,
    ) : BduiComponentUi {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.BUTTON
    }

    data class Input(
        override val baseProperties: BaseProperties,
        val text: BduiText,
        val placeholder: BduiText?,
        val hint: BduiText?,
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
        override val baseProperties: BaseProperties,
        override val children: List<BduiComponentUi>,
    ) : Container {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.COLUMN
    }

    data class Row(
        override val baseProperties: BaseProperties,
        override val children: List<BduiComponentUi>,
    ) : Container {
        override val type: BduiComponentTypeUi = BduiComponentTypeUi.ROW
    }

    data class Box(
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

@Immutable
sealed interface BduiActionUi {

    sealed interface Remote

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
    ) : BduiActionUi

    data class NavigateTo(
        val screenName: String,
        val screenNavigationParams: Map<String, JsonElement>?,
    ) : BduiActionUi

    data object NavigateBack : BduiActionUi

    data object Retry : BduiActionUi
}

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
