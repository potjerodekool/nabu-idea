package io.github.potjerodekool.nabuidea.language.rename

import com.intellij.patterns.ElementPattern
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameHelper
import com.intellij.psi.util.PsiTypesUtil
import com.intellij.psi.util.PsiUtil
import com.intellij.refactoring.rename.RenameInputValidator
import com.intellij.util.ProcessingContext

class NabuTypeRenameValidator : RenameInputValidator {
    override fun getPattern(): ElementPattern<out PsiElement?> {
        return PsiNabuPatterns.psiClass()
    }

    override fun isInputValid(
        newName: String,
        element: PsiElement,
        context: ProcessingContext
    ): Boolean {
         if (!element.isValid) {
            return false
        } else {
            val project = element.project
            val level = PsiUtil.getLanguageLevel(element)
            return PsiNameHelper.getInstance(project)
                .isIdentifier(newName, level) && !PsiTypesUtil.isRestrictedIdentifier(newName, level)
        }
    }
}