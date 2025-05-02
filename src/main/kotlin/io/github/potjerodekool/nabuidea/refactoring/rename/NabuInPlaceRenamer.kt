package io.github.potjerodekool.nabuidea.refactoring.rename

import com.intellij.lang.Language
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.command.impl.FinishMarkAction
import com.intellij.openapi.command.impl.StartMarkAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.impl.EditorImpl
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Comparing
import com.intellij.openapi.util.NlsContexts
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.impl.source.tree.injected.InjectedLanguageEditorUtil
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.RefactoringBundle
import com.intellij.refactoring.listeners.RefactoringEventData
import com.intellij.refactoring.listeners.RefactoringEventListener
import com.intellij.refactoring.rename.inplace.InplaceRefactoring
import com.intellij.util.SlowOperations
import com.intellij.util.concurrency.AppExecutorUtil
import java.util.concurrent.Callable

class NabuInPlaceRenamer(
    editor: Editor,
    elementToRename: PsiNamedElement,
    project: Project = elementToRename.project,
    initialName: String? = elementToRename.name,
    oldName: String? = elementToRename.name
) :
    InplaceRefactoring(editor, elementToRename, project, initialName, oldName) {

    private var origOffset: Int = 0
    private var language: Language? = null
    private var selectedRange: TextRange? = null

    override fun shouldSelectAll(): Boolean {
        return false
    }

    override fun getCommandName(): @NlsContexts.Command String? {
        return RefactoringBundle.message("renaming.command.name", myInitialName)
    }

    override fun performRefactoring(): Boolean {
        var bind = false

        if (myInsertedName != null) {
            val commandProcessor = CommandProcessor.getInstance()

            if (commandProcessor.currentCommand != null
                && getElementToRename() != null
            ) {
                commandProcessor.currentCommandName = commandName
            }

            bind = true
            ReadAction.nonBlocking( Callable<UnresolvableRenameProblem> { findProblem() })
                .expireWhen { myEditor.isDisposed || myProject.isDisposed }
                .finishOnUiThread(
                    ModalityState.nonModal(),
                    { problem ->
                        if (problem == null) {
                            SlowOperations.startSection(SlowOperations.ACTION_PERFORM).use {
                                performRefactoringRename(myInsertedName, myMarkAction)
                            }
                        } else {
                            problem.showUI()
                            cancel()
                        }
                    }
                ).submit { AppExecutorUtil.getAppExecutorService() }

            //TODO add code
        }

        return bind
    }

    private fun cancel() {
        try {
            stopDumbLaterIfPossible()
        } finally {
            FinishMarkAction.finish(myProject, myEditor, myMarkAction)
        }
    }

    private fun stopDumbLaterIfPossible() {
        if (InjectedLanguageEditorUtil.getTopLevelEditor(myEditor) is EditorImpl) {
            val editor = myEditor as EditorImpl
            editor.stopDumbLater()
        }
    }

    private fun performRefactoringRename(
        newName: String,
        myMarkAction: StartMarkAction) {

        val refactoringId = getRefactoringId()
        val myElementToRename = getElementToRename()


    }

    private fun findProblem(): UnresolvableRenameProblem? {
        if (!isIdentifier(myInsertedName, language)) {
            return IllegalIdentifierProblem()
        }

        return null
    }

    private fun getRefactoringId(): String? {
        return "refactoring.inplace.rename"
    }

    override fun beforeTemplateStart() {
        super.beforeTemplateStart()
        this.origOffset = myCaretRangeMarker.startOffset
        language = myScope.language

        val selectionModel = myEditor.selectionModel
        selectedRange =
            if (selectionModel.hasSelection()) TextRange(selectionModel.selectionStart, selectionModel.selectionEnd)
            else null
    }

    fun performInplaceRename(nameSuggestions: MutableList<String>?): Boolean {
        val refactoringId = getRefactoringId()
        val myElementToRename = getElementToRename()

        if (refactoringId != null) {
            val beforeData = RefactoringEventData()
            beforeData.addElement(myElementToRename)
            beforeData.addStringProperties(myOldName)
            myProject.messageBus
                .syncPublisher(RefactoringEventListener.REFACTORING_EVENT_TOPIC).refactoringStarted(
                    refactoringId,
                    beforeData
                )
        }

        return performInplaceRefactoring(
            if (nameSuggestions == null) null
            else LinkedHashSet(nameSuggestions)
        )
    }

    private fun getElementToRename() : PsiNamedElement? {
        if (myElementToRename != null
            && myElementToRename.isValid) {
                if (Comparing.strEqual(myOldName, myElementToRename.name)) {
                    return myElementToRename
                }

                if (myRenameOffset != null) {
                    return PsiTreeUtil.findElementOfClassAtRange(
                        myElementToRename.containingFile,
                        myRenameOffset.startOffset,
                        myRenameOffset.endOffset,
                        PsiNameIdentifierOwner::class.java
                    )
                }
        }

        if (myRenameOffset != null) {
            val file = PsiDocumentManager.getInstance(myProject).getPsiFile(myEditor.document)

            if (file != null) {
                return PsiTreeUtil.findElementOfClassAtRange(
                    file,
                    myRenameOffset.startOffset,
                    myRenameOffset.endOffset,
                    PsiNameIdentifierOwner::class.java
                )
            }
        }

        return myElementToRename
    }

}

private sealed interface UnresolvableRenameProblem {

    fun showUI()

}

class IllegalIdentifierProblem : UnresolvableRenameProblem {
    override fun showUI() {
        TODO("Not yet implemented")
    }

}