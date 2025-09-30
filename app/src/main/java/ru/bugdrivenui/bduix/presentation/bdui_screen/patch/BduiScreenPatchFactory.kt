package ru.bugdrivenui.bduix.presentation.bdui_screen.patch

import ru.bugdrivenui.bduix.data.model.action.ActionResponseModel
import ru.bugdrivenui.bduix.data.model.render.RenderedComponentModel
import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.utils.PresentationConstants.PATH_ROOT
import ru.bugdrivenui.bduix.presentation.utils.PresentationConstants.PATH_SEPARATOR
import ru.bugdrivenui.bduix.utils.pathToSegments
import javax.inject.Inject

class BduiScreenPatchFactory @Inject constructor() {

    fun createPatches(
        updates: List<ActionResponseModel.UpdateScreen.Response.Data>,
        factory: (RenderedComponentModel) -> BduiComponentUi,
    ): List<ComponentPatch> = updates.mapNotNull { updateData ->
        val segments = updateData.target.pathToSegments()
        if (segments.isEmpty()) return@mapNotNull null
        val parentPath = if (segments.size == 1) {
            PATH_ROOT
        } else {
            PATH_ROOT + segments.dropLast(1).joinToString(PATH_SEPARATOR)
        }
        val childId = segments.last()
        val method = when (updateData.method) {
            ActionResponseModel.UpdateScreen.Response.Data.ActionMethod.INSERT -> ComponentPatch.Method.INSERT
            ActionResponseModel.UpdateScreen.Response.Data.ActionMethod.UPDATE -> ComponentPatch.Method.UPDATE
            ActionResponseModel.UpdateScreen.Response.Data.ActionMethod.DELETE -> ComponentPatch.Method.DELETE
        }

        ComponentPatch(
            parentPath = parentPath,
            childId = childId,
            method = method,
            content = updateData.content?.let(factory),
        )
    }
}