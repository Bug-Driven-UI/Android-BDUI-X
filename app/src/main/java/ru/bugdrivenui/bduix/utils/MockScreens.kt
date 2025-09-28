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
                        //Spacer
                        BduiComponentUi.Spacer(
                            id = "column_spacer_id_1",
                            hash = "column_spacer_hash_1",
                            width = BduiComponentSize.MatchParent,
                            height = BduiComponentSize.Fixed(100),
                        ),
                        // Text 1
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
                        // Text 2
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
                        // Image 1
                        BduiComponentUi.Image(
                            id = "column_image_id1",
                            hash = "column_image_hash1",
                            interactions = null,
                            paddings = null,
                            margins = BduiComponentInsetsUi(
                                start = 16,
                                end = 16,
                                top = 16,
                                bottom = 8,
                            ),
                            width = BduiComponentSize.Fixed(200),
                            height = BduiComponentSize.Fixed(200),
                            backgroundColor = null,
                            border = BduiBorder(
                                color = BduiColor("#965EEB"),
                                thickness = 1,
                            ),
                            shape = BduiShape.RoundedCorners(
                                topStart = 16,
                                topEnd = 16,
                                bottomStart = 16,
                                bottomEnd = 16,
                            ),
                            imageUrl = "https://i.ytimg.com/vi/ArbiGwbF90A/maxresdefault.jpg?sqp=-oaymwEmCIAKENAF8quKqQMa8AEB-AH-CYAC0AWKAgwIABABGGUgUyhHMA8=&rs=AOn4CLDRBiDu5SDbBkAWmCnYDOp371Q7lw",
                        ),
                        // Text 3
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
                        // Spacer
                        BduiComponentUi.Spacer(
                            id = "column_spacer_id_1",
                            hash = "column_spacer_hash_1",
                            width = BduiComponentSize.MatchParent,
                            height = BduiComponentSize.Fixed(24),
                        ),
                        // Loader
                        BduiComponentUi.Loader(
                            id = "loader_1",
                            hash = "loader_hash_1",
                            width = BduiComponentSize.Fixed(40),
                            height = BduiComponentSize.Fixed(40),
                            color = BduiColor("#000000"),
                            strokeDp = 4,
                        ),
                        // Button
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
                ),


                // Input
                BduiComponentUi.Input(
                    id = "column_input_id1",
                    hash = "column_input_hash1",
                    interactions = null,
                    paddings = BduiComponentInsetsUi(
                        start = 16,
                        end = 16,
                        top = 12,
                        bottom = 12,
                    ),
                    margins = BduiComponentInsetsUi(
                        start = 16,
                        end = 16,
                        top = 16,
                        bottom = 0,
                    ),
                    width = BduiComponentSize.MatchParent,
                    height = BduiComponentSize.WrapContent,
                    backgroundColor = BduiColor("#1A965EEB"),
                    border = BduiBorder(
                        color = BduiColor("#965EEB"),
                        thickness = 2,
                    ),
                    shape = BduiShape.RoundedCorners(
                        topStart = 12,
                        topEnd = 12,
                        bottomStart = 12,
                        bottomEnd = 12,
                    ),
                    text = BduiText(
                        value = "",
                        color = BduiColor("#000000"),
                        style = BduiTextStyle(
                            decorationType = BduiTextDecorationType.REGULAR,
                            weight = 400,
                            size = 15,
                        )
                    ),
                    placeholder = BduiText(
                        value = "Введите текст",
                        color = BduiColor("#000000"),
                        style = BduiTextStyle(
                            decorationType = BduiTextDecorationType.REGULAR,
                            weight = 400,
                            size = 15,
                        )
                    ),
                    hint = BduiText(
                        value = "Подсказка под полем",
                        color = BduiColor("#965EEB"),
                        style = BduiTextStyle(
                            decorationType = BduiTextDecorationType.REGULAR,
                            weight = 400,
                            size = 13,
                        )
                    ),
                ),
            )
        ),
        // end 1
    )
}