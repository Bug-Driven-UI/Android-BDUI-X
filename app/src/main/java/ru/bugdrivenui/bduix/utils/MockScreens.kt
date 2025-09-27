package ru.bugdrivenui.bduix.utils

import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toBduiColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiBorder
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentInsetsUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentInteractionsUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentSize
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiScreenUiModel
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiShape
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiText
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiTextDecorationType
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiTextStyle

object MockScreens {

    val data = listOf(
        // 1
        BduiScreenUiModel(
            screenId = "screenId",
            components = listOf(
                BduiComponentUi.Column(
                    id = "column_id",
                    hash = "column_hash",
                    interactions = null,
                    paddings = null,
                    margins = null,
                    width = BduiComponentSize.MatchParent,
                    height = BduiComponentSize.WrapContent,
                    backgroundColor = null,
                    children = listOf(
                        BduiComponentUi.Text(
                            id = "column_text_id1",
                            hash = "column_text_hash1",
                            interactions = null,
                            paddings = null,
                            margins = null,
                            width = BduiComponentSize.WrapContent,
                            height = BduiComponentSize.WrapContent,
                            text = BduiText(
                                value = "column_text1",
                                color = BduiColor.Default,
                                style = BduiTextStyle(
                                    decorationType = BduiTextDecorationType.REGULAR,
                                    weight = 600,
                                    size = 15,
                                )
                            ),
                            backgroundColor = BduiColor("#000000"),
                            shape = null,
                            border = null,
                        ),
                        BduiComponentUi.Text(
                            id = "column_text_id2",
                            hash = "column_text_hash2",
                            interactions = null,
                            paddings = BduiComponentInsetsUi(
                                start = 16,
                                end = 16,
                                top = 16,
                                bottom = 16,
                            ),
                            margins = BduiComponentInsetsUi(
                                start = 32,
                                end = 16,
                                top = 16,
                                bottom = 0,
                            ),
                            width = BduiComponentSize.WrapContent,
                            height = BduiComponentSize.Fixed(100),
                            backgroundColor = BduiColor("#000000"),
                            text = BduiText(
                                value = "column_text2",
                                color = BduiColor.Default,
                                style = BduiTextStyle(
                                    decorationType = BduiTextDecorationType.REGULAR,
                                    weight = 500,
                                    size = 15,
                                )
                            ),
                            shape = BduiShape.RoundedCorners(
                                topStart = 24,
                                topEnd = 24,
                                bottomStart = 24,
                                bottomEnd = 24,
                            ),
                            border = BduiBorder(
                                color = BduiColor.Default,
                                thickness = 3,
                            ),
                        ),
                        BduiComponentUi.Text(
                            id = "column_text_id3",
                            hash = "column_text_hash3",
                            interactions = null,
                            paddings = null,
                            margins = null,
                            width = BduiComponentSize.WrapContent,
                            height = BduiComponentSize.WrapContent,
                            backgroundColor = BduiColor("#00FFFFFF"),
                            text = BduiText(
                                value = "column_text3",
                                color = BduiColor("#000000"),
                                style = BduiTextStyle(
                                    decorationType = BduiTextDecorationType.STRIKETHROUGH,
                                    weight = 500,
                                    size = 15,
                                )
                            ),
                            shape = null,
                            border = null,
                        ),
                        BduiComponentUi.Button(
                            id = "column_button_id1",
                            hash = "column_button_hash1",
                            interactions = BduiComponentInteractionsUi(
                                onClick = listOf(
                                    BduiActionUi.Command(
                                        name = "command_name1",
                                        params = null,
                                    ),
                                ),
                                onShow = null,
                            ),
                            paddings = null,
                            margins = BduiComponentInsetsUi(
                                start = 0,
                                end = 0,
                                top = 16,
                                bottom = 0,
                            ),
                            width = BduiComponentSize.WrapContent,
                            height = BduiComponentSize.WrapContent,
                            backgroundColor = "#965EEB".toBduiColor(),
                            border = null,
                            shape = BduiShape.RoundedCorners(
                                topStart = 16,
                                topEnd = 16,
                                bottomStart = 16,
                                bottomEnd = 16,
                            ),
                            text = BduiText(
                                value = "Оформить доставку",
                                color = "#FFFFFF".toBduiColor(),
                                style = BduiTextStyle(
                                    decorationType = BduiTextDecorationType.REGULAR,
                                    weight = 500,
                                    size = 15,
                                ),
                            ),
                            enabled = true,
                        )
                    ),
                    border = null,
                    shape = null,
                )
            )
        ),
        // end 1
    )
}