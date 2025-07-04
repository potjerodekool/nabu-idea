package io.github.potjerodekool.nabuidea.language.rename

import com.intellij.patterns.ElementPattern
import com.intellij.psi.PsiElement
import com.intellij.refactoring.rename.RenameInputValidator
import com.intellij.util.ProcessingContext

class NabuTypeRenameValidator : RenameInputValidator {
    override fun getPattern(): ElementPattern<out PsiElement?> {
        TODO()
    }

    override fun isInputValid(
        newName: String,
        element: PsiElement,
        context: ProcessingContext
    ): Boolean {
        return true
    }
}