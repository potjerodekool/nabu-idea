package io.github.potjerodekool.nabuidea.refactoring.rename

import com.intellij.ide.IdeEventQueue
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.refactoring.rename.RenameHandlerRegistry
import com.intellij.refactoring.rename.Renamer
import com.intellij.refactoring.rename.RenamerFactory
import com.intellij.util.containers.ContainerUtil
import org.jetbrains.annotations.Unmodifiable

class NabuRenamerFactory : RenamerFactory {

    override fun createRenamers(dataContext: DataContext): @Unmodifiable Collection<Renamer> {
        val project = dataContext.getData(CommonDataKeys.PROJECT)

        if (project == null) {
            return emptyList()
        }

        if (!isAvailable(dataContext)) {
            return emptyList()
        }

        val eventCount = IdeEventQueue.getInstance().eventCount
        return ContainerUtil.map(
            RenameHandlerRegistry.getInstance().getRenameHandlers(dataContext),
            { renameHandler -> NabuRenamer(project, dataContext, renameHandler, eventCount) }
        )
    }

    private fun isAvailable(dataContext: DataContext): Boolean {
        //TODO implement
        return true
    }
}