package ru.bugdrivenui.bduix.presentation.bdui_screen.hash

import ru.bugdrivenui.bduix.data.model.action.HashNode
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import javax.inject.Inject

class BduiScreenHashCollector @Inject constructor() {

    fun collect(
        componentTree: List<BduiComponentUi>,
    ): List<HashNode> {
        return componentTree.map(::collectInternal)
    }

    private fun collectInternal(component: BduiComponentUi): HashNode {
        return HashNode(
            id = component.baseProperties.id,
            hash = component.baseProperties.hash,
            children = (component as? BduiComponentUi.Container)
                ?.children
                ?.map(::collectInternal)
                .orEmpty(),
        )
    }
}