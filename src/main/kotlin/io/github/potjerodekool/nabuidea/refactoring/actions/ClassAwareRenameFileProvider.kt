package io.github.potjerodekool.nabuidea.refactoring.actions

import com.intellij.psi.PsiFile
import com.intellij.refactoring.actions.RenameFileActionProvider
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuPsiClassOwner

class ClassAwareRenameFileProvider : RenameFileActionProvider {

    override fun enabledInProjectView(file: PsiFile): Boolean {
        return file is NabuPsiClassOwner
    }
}