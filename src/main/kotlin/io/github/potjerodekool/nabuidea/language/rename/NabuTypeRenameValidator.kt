package io.github.potjerodekool.nabuidea.language.rename

import com.intellij.patterns.ElementPattern
import com.intellij.psi.PsiElement
import com.intellij.refactoring.rename.RenameInputValidator
import com.intellij.util.ProcessingContext
import io.github.potjerodekool.nabuidea.pattern.PsiNabuPatterns

class NabuTypeRenameValidator : RenameInputValidator {
    override fun getPattern(): ElementPattern<out PsiElement?> {
        return PsiNabuPatterns.psiClass()
    }

    override fun isInputValid(
        newName: String,
        element: PsiElement,
        context: ProcessingContext
    ): Boolean {
        return true
    }
}