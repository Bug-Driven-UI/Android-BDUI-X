package ru.bugdrivenui.bduix.presentation.bdui_screen.factory

import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toBduiColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiText
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiTextDecorationType
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiTextStyle
import javax.inject.Inject

class BduiComponentFactory @Inject constructor() {

//    fun create(
//        component: BduiComponentResponse,
//    ): BduiComponentUi {
//        return when (component.type) {
//            BduiComponentType.TEXT -> createTextComponent(component)
//            BduiComponentType.IMAGE -> TODO()
//            BduiComponentType.BUTTON -> TODO()
//            BduiComponentType.INPUT -> TODO()
//            BduiComponentType.ROW -> TODO()
//            BduiComponentType.COLUMN -> createColumnComponent(component)
//            BduiComponentType.BOX -> TODO()
//        }
//    }
//
//    private fun createTextComponent(
//        component: BduiComponentResponse,
//    ): BduiComponentUi {
//        return BduiComponentUi.Text(
//            id = component.id,
//            hash = component.hash,
//            interactions = component.interactions?.toBduiInteractions(),
//            paddings = component.paddings.toComponentInsets(),
//            margins = component.margins.toComponentInsets(),
//            width = component.width.toComponentSize(),
//            height = component.height.toComponentSize(),
//            backgroundColor = component.backgroundColor.toBduiColor(),
//            text = BduiText(
//                value = component.text.orEmpty(),
//                color = component.color.toBduiColor(),
//                style = BduiTextStyle(
//                    decorationType = BduiTextDecorationType.REGULAR,
//                    weight = 600,
//                    size = 15,
//                )
//            ),
//            border = component.border.toBduiBorder(),
//            shape = component.shape.toBduiShape(),
//        )
//    }
//
//    private fun createColumnComponent(
//        component: BduiComponentResponse,
//    ): BduiComponentUi {
//        return BduiComponentUi.Column(
//            id = component.id,
//            hash = component.hash,
//            interactions = component.interactions?.toBduiInteractions(),
//            paddings = component.paddings.toComponentInsets(),
//            margins = component.margins.toComponentInsets(),
//            width = component.width.toComponentSize(),
//            height = component.height.toComponentSize(),
//            backgroundColor = component.backgroundColor.toBduiColor(),
//            children = component.children?.map(::create).orEmpty(),
//            border = component.border.toBduiBorder(),
//            shape = component.shape.toBduiShape(),
//        )
//    }
}