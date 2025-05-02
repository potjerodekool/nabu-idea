package io.github.potjerodekool.nabuidea.refactoring.rename

import com.intellij.ide.TitledHandler
import com.intellij.lang.LanguageRefactoringSupport
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.ScrollType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.NlsActions
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.PsiReference
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.RefactoringBundle
import com.intellij.refactoring.rename.PsiElementRenameHandler
import com.intellij.refactoring.rename.RenameHandler
import com.intellij.refactoring.rename.RenameHandlerRegistry
import com.intellij.refactoring.rename.inplace.InplaceRefactoring

// VariableInplaceRenameHandler
class NabuRenameInPlaceHandler : RenameHandler, TitledHandler {

    private val preventInlineRenameFlag = ThreadLocal<String?>()

    override fun isAvailableOnDataContext(dataContext: DataContext): Boolean {
        val element = PsiElementRenameHandler.getElement(dataContext)
        val editor = CommonDataKeys.EDITOR.getData(dataContext)
        val file = CommonDataKeys.PSI_FILE.getData(dataContext)

        if (editor == null
            || file == null
        ) {
            return false
        }

        if (preventInlineRenameFlag.get() != null) {
            return false
        }

        return isAvailable(element, editor, file)
    }

    private fun isAvailable(
        element: PsiElement?,
        editor: Editor,
        file: PsiFile): Boolean {
        val nameSuggestionContext = file.findElementAt(editor.caretModel.offset)

        if (element == null
            || !element.isValid) {
            return false
        }

        val supportProvider = LanguageRefactoringSupport.getInstance().forContext(element)

        return supportProvider != null
                && supportProvider.isInplaceRenameAvailable(element, nameSuggestionContext)
    }

    override fun invoke(
        project: Project,
        editor: Editor?,
        file: PsiFile?,
        dataContext: DataContext?
    ) {
        if (editor == null
            || file == null
            || dataContext == null
        ) {
            return
        }

        var element = PsiElementRenameHandler.getElement(dataContext)

        if (element == null) {
            val foundElement = file.findElementAt(editor.caretModel.offset)

            if (foundElement != null) {
                val parent = foundElement.parent

                element = if (parent is PsiReference) parent.resolve()
                    else PsiTreeUtil.getParentOfType(foundElement, PsiNamedElement::class.java)
            }
        }

        if (element == null) {
            return
        }

        editor.scrollingModel.scrollToCaret(ScrollType.MAKE_VISIBLE)

        if (checkAvailable(element, editor, dataContext)) {
            doRename(element, editor, dataContext)
        }
    }

    private fun doRename(
        elementToRename: PsiElement,
        editor: Editor,
        dataContext: DataContext
    ): InplaceRefactoring? {
        if (elementToRename is PsiNamedElement) {
            val renamer = createRenamer(elementToRename, editor)
            val names = PsiElementRenameHandler.NAME_SUGGESTIONS.getData(dataContext)
            val startedRename = renamer.performInplaceRename(names)

            if (!startedRename) {
                performDialogRename(elementToRename, editor, dataContext, renamer.initialName)
            }

            return renamer
        }

        return null
    }

    private fun performDialogRename(
        elementToRename: PsiNamedElement,
        editor: Editor,
        dataContext: DataContext,
        initialName: String
    ) {
        try {
            preventInlineRenameFlag.set(initialName)
            val handler = RenameHandlerRegistry.getInstance().getRenameHandler(dataContext)
            handler?.invoke(
                elementToRename.project,
                editor,
                elementToRename.containingFile,
                dataContext
            )
        } finally {
            preventInlineRenameFlag.remove()
        }
    }

    private fun createRenamer(
        elementToRename: PsiNamedElement,
        editor: Editor
    ): NabuInPlaceRenamer {
        return NabuInPlaceRenamer(
            editor,
            elementToRename
        )
    }

    private fun checkAvailable(
        elementToRename: PsiElement,
        editor: Editor,
        dataContext: DataContext
    ): Boolean {

        if (!isAvailableOnDataContext(dataContext)) {
            val handler = RenameHandlerRegistry.getInstance().getRenameHandler(dataContext)

            if (handler == null) {
                return false
            }

            handler.invoke(
                elementToRename.project,
                editor,
                elementToRename.containingFile,
                dataContext
            )
            return false
        }

        return true
    }

    override fun invoke(
        project: Project,
        elements: Array<out PsiElement?>,
        dataContext: DataContext
    ) {
        var element = if (elements.size == 1) elements[0] else null

        if (element == null) {
            element = PsiElementRenameHandler.getElement(dataContext)
        }

        if (element == null) {
            return
        }

        val editor = CommonDataKeys.EDITOR.getData(dataContext)!!

        if (checkAvailable(element, editor, dataContext)) {
            doRename(element, editor, dataContext)
        }
    }

    override fun getActionTitle(): @NlsActions.ActionText String? {
        return RefactoringBundle.message("rename.title")
    }

}

