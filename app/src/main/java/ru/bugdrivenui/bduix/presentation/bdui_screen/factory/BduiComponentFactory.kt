package ru.bugdrivenui.bduix.presentation.bdui_screen.factory

import ru.bugdrivenui.bduix.data.model.RenderedActionModel
import ru.bugdrivenui.bduix.data.model.render.RenderedComponentModel
import ru.bugdrivenui.bduix.data.model.render.RenderedHorizontalAlignment
import ru.bugdrivenui.bduix.data.model.render.RenderedHorizontalAndVerticalAlignment
import ru.bugdrivenui.bduix.data.model.render.RenderedHorizontalArrangement
import ru.bugdrivenui.bduix.data.model.render.RenderedVerticalAlignment
import ru.bugdrivenui.bduix.data.model.render.RenderedVerticalArrangement
import ru.bugdrivenui.bduix.presentation.bdui_screen.local_state.LocalStateResolver
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.BduiComponentPropertiesMapper
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toBduiBorder
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toBduiColor
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toBduiShape
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toComponentInsets
import ru.bugdrivenui.bduix.presentation.bdui_screen.mapper.toComponentSize
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiActionUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiHorizontalAlignment
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiHorizontalAndVerticalAlignment
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiHorizontalArrangement
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiVerticalAlignment
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiVerticalArrangement
import javax.inject.Inject

class BduiComponentFactory @Inject constructor(
    private val localStateResolver: LocalStateResolver,
    private val mapper: BduiComponentPropertiesMapper,
) {

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
            interactions = component.interactions.let(mapper::toBduiInteractions),
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
            contentAlignment = component.contentAlignment.toBduiAlignment(),
            baseProperties = createBaseProperties(component),
            children = component.children.map(::create),
        )
    }

    private fun createRowComponent(
        component: RenderedComponentModel.Row,
    ): BduiComponentUi {
        return BduiComponentUi.Row(
            horizontalArrangement = component.horizontalArrangement.toBduiArrangement(),
            verticalAlignment = component.verticalAlignment.toBduiAlignment(),
            isScrollable = component.isScrollable ?: false,
            baseProperties = createBaseProperties(component),
            children = component.children.map(::create),
        )
    }

    private fun createColumnComponent(
        component: RenderedComponentModel.Column,
    ): BduiComponentUi {
        return BduiComponentUi.Column(
            verticalArrangement = component.verticalArrangement.toBduiArrangement(),
            horizontalAlignment = component.horizontalAlignment.toBduiAlignment(),
            baseProperties = createBaseProperties(component),
            children = component.children.map(::create),
        )
    }

    private fun createTextComponent(
        component: RenderedComponentModel.Text,
    ): BduiComponentUi {
        return BduiComponentUi.Text(
            baseProperties = createBaseProperties(component),
            text = mapper.toBduiText(component.textWithStyle),
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
    ): BduiComponentUi.Image {
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
            text = component.textWithStyle.let(mapper::toBduiText),
            placeholder = component.placeholder?.textWithStyle?.let(mapper::toBduiText),
            rightIcon = component.rightIcon?.let(::createImageComponent),
            onValueChangedActions = buildList {
                component.onValueChanged?.forEach { action ->
                    when (action) {
                        is RenderedActionModel.RenderedSetLocalStateFromInputActionModel -> {
                            localStateResolver.resolveRawPath(action.target)?.let { path ->
                                BduiActionUi.SetLocalStateFromInput(path)
                            }
                        }
                        else -> null
                    }?.let(::add)
                }
            }
        )
    }

    private fun createSpacerComponent(
        component: RenderedComponentModel.Spacer,
    ): BduiComponentUi {
        return BduiComponentUi.Spacer(
            baseProperties = createBaseProperties(component),
        )
    }

    private fun RenderedHorizontalAndVerticalAlignment?.toBduiAlignment() = this?.let {
        when (this) {
            RenderedHorizontalAndVerticalAlignment.BottomCenter -> BduiHorizontalAndVerticalAlignment.BottomCenter
            RenderedHorizontalAndVerticalAlignment.BottomEnd -> BduiHorizontalAndVerticalAlignment.BottomEnd
            RenderedHorizontalAndVerticalAlignment.BottomStart -> BduiHorizontalAndVerticalAlignment.BottomStart
            RenderedHorizontalAndVerticalAlignment.Center -> BduiHorizontalAndVerticalAlignment.Center
            RenderedHorizontalAndVerticalAlignment.CenterEnd -> BduiHorizontalAndVerticalAlignment.CenterEnd
            RenderedHorizontalAndVerticalAlignment.CenterStart -> BduiHorizontalAndVerticalAlignment.CenterStart
            RenderedHorizontalAndVerticalAlignment.TopCenter -> BduiHorizontalAndVerticalAlignment.TopCenter
            RenderedHorizontalAndVerticalAlignment.TopEnd -> BduiHorizontalAndVerticalAlignment.TopEnd
            RenderedHorizontalAndVerticalAlignment.TopStart -> BduiHorizontalAndVerticalAlignment.TopStart
        }
    }

    private fun RenderedVerticalArrangement?.toBduiArrangement() = this?.let {
        when (this) {
            RenderedVerticalArrangement.Bottom -> BduiVerticalArrangement.Bottom
            RenderedVerticalArrangement.Center -> BduiVerticalArrangement.Center
            RenderedVerticalArrangement.SpaceAround -> BduiVerticalArrangement.SpaceAround
            RenderedVerticalArrangement.SpaceBetween -> BduiVerticalArrangement.SpaceBetween
            RenderedVerticalArrangement.SpaceEvenly -> BduiVerticalArrangement.SpaceEvenly
            RenderedVerticalArrangement.Top -> BduiVerticalArrangement.Top
        }
    }

    private fun RenderedHorizontalAlignment?.toBduiAlignment() = this?.let {
        when (this) {
            RenderedHorizontalAlignment.Center -> BduiHorizontalAlignment.Center
            RenderedHorizontalAlignment.End -> BduiHorizontalAlignment.End
            RenderedHorizontalAlignment.Start -> BduiHorizontalAlignment.Start
        }
    }

    private fun RenderedHorizontalArrangement?.toBduiArrangement() = this?.let {
        when (this) {
            RenderedHorizontalArrangement.Center -> BduiHorizontalArrangement.Center
            RenderedHorizontalArrangement.End -> BduiHorizontalArrangement.End
            RenderedHorizontalArrangement.SpaceAround -> BduiHorizontalArrangement.SpaceAround
            RenderedHorizontalArrangement.SpaceBetween -> BduiHorizontalArrangement.SpaceBetween
            RenderedHorizontalArrangement.SpaceEvenly -> BduiHorizontalArrangement.SpaceEvenly
            RenderedHorizontalArrangement.Start -> BduiHorizontalArrangement.Start
        }
    }

    private fun RenderedVerticalAlignment?.toBduiAlignment() = this?.let {
        when (this) {
            RenderedVerticalAlignment.Bottom -> BduiVerticalAlignment.Bottom
            RenderedVerticalAlignment.Center -> BduiVerticalAlignment.Center
            RenderedVerticalAlignment.Top -> BduiVerticalAlignment.Top
        }
    }
}