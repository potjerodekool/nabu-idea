package io.github.potjerodekool.nabuidea.refactoring.actions

import com.intellij.psi.PsiClassOwner
import com.intellij.psi.PsiFile
import com.intellij.refactoring.actions.RenameFileActionProvider

class ClassAwareRenameFileProvider : RenameFileActionProvider {

    override fun enabledInProjectView(file: PsiFile): Boolean {
        return file is PsiClassOwner
    }
}