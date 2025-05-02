package io.github.potjerodekool.nabuidea.language

import com.intellij.lang.refactoring.RefactoringSupportProvider
import com.intellij.psi.PsiElement
import com.intellij.refactoring.RefactoringActionHandler

class NabuRefactoringSupportProvider : RefactoringSupportProvider() {

    override fun isSafeDeleteAvailable(element: PsiElement): Boolean {
        return false
    }

    override fun isInplaceRenameAvailable(element: PsiElement, context: PsiElement?): Boolean {
        return true
    }

    override fun isAvailable(context: PsiElement): Boolean {
        return true
    }

    override fun isInplaceIntroduceAvailable(element: PsiElement, context: PsiElement?): Boolean {
        return true
    }

    override fun isMemberInplaceRenameAvailable(element: PsiElement, context: PsiElement?): Boolean {
        return true
    }

    override fun getIntroduceVariableHandler(): RefactoringActionHandler? {
        return super.getIntroduceVariableHandler()
    }



}