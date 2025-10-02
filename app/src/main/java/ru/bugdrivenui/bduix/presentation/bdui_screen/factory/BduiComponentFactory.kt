package ru.bugdrivenui.bduix.presentation.bdui_screen.factory

import ru.bugdrivenui.bduix.data.model.render.RenderedComponentModel
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toBduiBorder
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toBduiColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toBduiInteractions
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toBduiShape
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toBduiText
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toComponentInsets
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toComponentSize
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import javax.inject.Inject

class BduiComponentFactory @Inject constructor() {

    fun create(
        component: RenderedComponentModel,
    ): BduiComponentUi {
        return when (component) {
            is RenderedComponentModel.Box -> createBoxComponent(component)
            is RenderedComponentModel.Button -> createButtonComponent(component)
            is RenderedComponentModel.Column -> createColumnComponent(component)
            is RenderedComponentModel.Image -> createImageComponent(component)
            is RenderedComponentModel.Input -> createInputComponent(component)
            is RenderedComponentModel.Row -> createRowComponent(component)
            is RenderedComponentModel.Spacer -> createSpacerComponent(component)
            is RenderedComponentModel.Switch -> TODO()
            is RenderedComponentModel.Text -> createTextComponent(component)
        }
    }

    private fun createBaseProperties(
        component: RenderedComponentModel,
        customizeBlock: (BduiComponentUi.BaseProperties) -> BduiComponentUi.BaseProperties = { it },
    ): BduiComponentUi.BaseProperties {
        return BduiComponentUi.BaseProperties(
            id = component.id,
            hash = component.hash,
            interactions = component.interactions.toBduiInteractions(),
            paddings = component.paddings.toComponentInsets(),
            margins = component.margins.toComponentInsets(),
            width = component.width.toComponentSize(),
            height = component.height.toComponentSize(),
            backgroundColor = component.backgroundColor.toBduiColor(),
            border = component.border.toBduiBorder(),
            shape = component.shape.toBduiShape(),
        ).let(customizeBlock)
    }

    private fun createBoxComponent(
        component: RenderedComponentModel.Box,
    ): BduiComponentUi {
        return BduiComponentUi.Box(
            baseProperties = createBaseProperties(component),
            children = component.children.map(::create),
        )
    }

    private fun createRowComponent(
        component: RenderedComponentModel.Row,
    ): BduiComponentUi {
        return BduiComponentUi.Row(
            baseProperties = createBaseProperties(component),
            children = component.children.map(::create),
        )
    }

    private fun createColumnComponent(
        component: RenderedComponentModel.Column,
    ): BduiComponentUi {
        return BduiComponentUi.Column(
            baseProperties = createBaseProperties(component),
            children = component.children.map(::create),
        )
    }

    private fun createTextComponent(
        component: RenderedComponentModel.Text,
    ): BduiComponentUi {
        return BduiComponentUi.Text(
            baseProperties = createBaseProperties(component),
            text = component.textWithStyle.toBduiText(),
        )
    }

    private fun createButtonComponent(
        component: RenderedComponentModel.Button,
    ): BduiComponentUi {
        return BduiComponentUi.Button(
            baseProperties = createBaseProperties(component),
            enabled = component.enabled,
            text = createTextComponent(component.text) as BduiComponentUi.Text,
        )
    }

    private fun createImageComponent(
        component: RenderedComponentModel.Image,
    ): BduiComponentUi {
        return BduiComponentUi.Image(
            baseProperties = createBaseProperties(component) { it.copy(backgroundColor = null) },
            imageUrl = component.imageUrl,
        )
    }

    private fun createInputComponent(
        component: RenderedComponentModel.Input,
    ): BduiComponentUi {
        return BduiComponentUi.Input(
            baseProperties = createBaseProperties(component),
            text = component.textWithStyle.toBduiText(),
            placeholder = component.placeholder?.textWithStyle?.toBduiText(),
            hint = component.hint?.textWithStyle?.toBduiText(),
        )
    }

    private fun createSpacerComponent(
        component: RenderedComponentModel.Spacer,
    ): BduiComponentUi {
        return BduiComponentUi.Spacer(
            baseProperties = createBaseProperties(component),
        )
    }
}