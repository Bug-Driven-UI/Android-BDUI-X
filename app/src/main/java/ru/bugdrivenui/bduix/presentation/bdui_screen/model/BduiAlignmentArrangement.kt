package ru.bugdrivenui.bduix.presentation.bdui_screen.model

sealed interface BduiHorizontalArrangement {
    data object Start : BduiHorizontalArrangement
    data object End : BduiHorizontalArrangement
    data object Center : BduiHorizontalArrangement
    data object SpaceBetween : BduiHorizontalArrangement
    data object SpaceEvenly : BduiHorizontalArrangement
    data object SpaceAround : BduiHorizontalArrangement
}

sealed interface BduiVerticalArrangement {
    data object Top : BduiVerticalArrangement
    data object Bottom : BduiVerticalArrangement
    data object Center : BduiVerticalArrangement
    data object SpaceBetween : BduiVerticalArrangement
    data object SpaceEvenly : BduiVerticalArrangement
    data object SpaceAround : BduiVerticalArrangement
}

sealed interface BduiHorizontalAlignment {
    data object Start : BduiHorizontalAlignment
    data object Center : BduiHorizontalAlignment
    data object End : BduiHorizontalAlignment
}

sealed interface BduiVerticalAlignment {
    data object Top : BduiVerticalAlignment
    data object Center : BduiVerticalAlignment
    data object Bottom : BduiVerticalAlignment
}

sealed interface BduiHorizontalAndVerticalAlignment {
    data object TopStart : BduiHorizontalAndVerticalAlignment
    data object TopCenter : BduiHorizontalAndVerticalAlignment
    data object TopEnd : BduiHorizontalAndVerticalAlignment
    data object CenterStart : BduiHorizontalAndVerticalAlignment
    data object Center : BduiHorizontalAndVerticalAlignment
    data object CenterEnd : BduiHorizontalAndVerticalAlignment
    data object BottomStart : BduiHorizontalAndVerticalAlignment
    data object BottomCenter : BduiHorizontalAndVerticalAlignment
    data object BottomEnd : BduiHorizontalAndVerticalAlignment
}
