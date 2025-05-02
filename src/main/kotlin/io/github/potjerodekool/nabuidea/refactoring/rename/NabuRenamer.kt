package io.github.potjerodekool.nabuidea.refactoring.rename

import com.intellij.ide.IdeEventQueue
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.project.Project
import com.intellij.refactoring.actions.BaseRefactoringAction
import com.intellij.refactoring.rename.RenameHandler
import com.intellij.refactoring.rename.RenameHandlerRegistry
import com.intellij.refactoring.rename.Renamer
import com.intellij.util.SlowOperations
import com.intellij.util.ui.UIUtil
import org.jetbrains.annotations.Nls

class NabuRenamer(private val project: Project,
                  private val dataContext: DataContext,
                  private val renameHandler: RenameHandler?,
                  private val eventCount: Int) : Renamer {

    override fun getPresentableText(): @Nls(capitalization = Nls.Capitalization.Sentence) String {
        return UIUtil.removeMnemonic(
            RenameHandlerRegistry.getHandlerTitle(renameHandler)
        )
    }

    override fun performRename() {
        SlowOperations.startSection(SlowOperations.ACTION_PERFORM).use {
            IdeEventQueue.getInstance().eventCount = eventCount
            BaseRefactoringAction.performRefactoringAction(
                project,
                dataContext,
                renameHandler
            )
        }
    }
}