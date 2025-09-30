package ru.bugdrivenui.bduix.presentation.bdui_screen.patch

import ru.bugdrivenui.bduix.presentation.bdui_screen.model.BduiComponentUi
import ru.bugdrivenui.bduix.presentation.utils.PresentationConstants.PATH_ROOT
import ru.bugdrivenui.bduix.presentation.utils.PresentationConstants.PATH_SEPARATOR
import ru.bugdrivenui.bduix.presentation.utils.PresentationConstants.PATH_SEPARATOR_CHAR
import ru.bugdrivenui.bduix.utils.orZero
import ru.bugdrivenui.bduix.utils.pathToSegments
import javax.inject.Inject

class BduiScreenPatchManager @Inject constructor() {

    fun applyPatchesToRoot(
        rootChildren: List<BduiComponentUi>,
        patches: List<ComponentPatch>,
    ): List<BduiComponentUi> {
        if (patches.isEmpty()) return rootChildren
        val patchGroups = groupPatchesByParent(patches)
        val prefixSet = buildPrefixSet(patches)

        return applyPatchesToChildren(
            currentPath = PATH_ROOT,
            children = rootChildren,
            patchGroups = patchGroups,
            prefixSet = prefixSet,
        )
    }

    private fun applyPatchesToChildren(
        currentPath: String,
        children: List<BduiComponentUi>,
        patchGroups: Map<String, ComponentPatchGroup>,
        prefixSet: Set<String>,
    ): List<BduiComponentUi> {
        if (currentPath !in prefixSet) return children

        val patchGroup = patchGroups[currentPath]
        val currentComponentHasLocalOperations = patchGroup != null && (
                patchGroup.updates.isNotEmpty()
                        || patchGroup.inserts.isNotEmpty()
                        || patchGroup.deletes.isNotEmpty()
                )

        val result =
            ArrayList<BduiComponentUi>(children.size + (patchGroup?.inserts?.size.orZero()))

        if (patchGroup?.inserts?.isNotEmpty() == true) {
            for ((_, newComponent) in patchGroup.inserts) {
                result.add(newComponent)
            }
        }

        children.forEach { child ->
            val childId = child.baseProperties.id
            if (patchGroup?.deletes?.contains(childId) == true) return@forEach
            val newComponent = patchGroup?.updates?.get(childId) ?: run {
                if (child is BduiComponentUi.Container) {
                    val childPath = joinPath(currentPath, childId)
                    val patchedChildren = applyPatchesToChildren(
                        currentPath = childPath,
                        children = child.children,
                        patchGroups = patchGroups,
                        prefixSet = prefixSet,
                    )
                    if (patchedChildren === child.children) child else child.copy(patchedChildren)
                } else {
                    child
                }
            }
            result.add(newComponent)
        }

        return if (
            !currentComponentHasLocalOperations
            && result.size == children.size
            && result.zip(children).all { (new, old) -> new === old }
        ) children else result
    }

    private fun groupPatchesByParent(patches: List<ComponentPatch>): Map<String, ComponentPatchGroup> {
        val groups = mutableMapOf<String, ComponentPatchGroup>()
        patches.forEach { patch ->
            val group = groups.getOrPut(patch.parentPath) { ComponentPatchGroup() }
            when (patch.method) {
                ComponentPatch.Method.INSERT -> patch.content?.let {
                    group.inserts[patch.childId] = it
                }

                ComponentPatch.Method.UPDATE -> patch.content?.let {
                    group.updates[patch.childId] = it
                }

                ComponentPatch.Method.DELETE -> group.deletes.add(patch.childId)
            }
        }
        return groups
    }

    private fun buildPrefixSet(patches: List<ComponentPatch>): Set<String> {
        val prefixSet = mutableSetOf<String>()
        patches.forEach { patch ->
            var path = patch.parentPath.trimEnd(PATH_SEPARATOR_CHAR)
            if (path.isEmpty()) path = PATH_ROOT
            val segments = path.pathToSegments()
            for (index in segments.indices) {
                val prefix = PATH_ROOT + segments.take(index + 1).joinToString(PATH_SEPARATOR)
                prefixSet.add(prefix)
            }
            prefixSet.add(PATH_ROOT)
        }
        return prefixSet
    }

    private fun joinPath(parentPath: String, childId: String): String =
        if (parentPath == PATH_ROOT) "$PATH_ROOT$childId" else "$parentPath$PATH_SEPARATOR$childId"

    private fun BduiComponentUi.Container.copy(newChildren: List<BduiComponentUi>): BduiComponentUi.Container {
        return when (this) {
            is BduiComponentUi.Box -> copy(children = newChildren)
            is BduiComponentUi.Column -> copy(children = newChildren)
            is BduiComponentUi.Row -> copy(children = newChildren)
        }
    }
}