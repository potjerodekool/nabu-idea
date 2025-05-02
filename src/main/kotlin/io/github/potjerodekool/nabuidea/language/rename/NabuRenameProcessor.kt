package io.github.potjerodekool.nabuidea.language.rename

import com.intellij.psi.PsiElement
import com.intellij.refactoring.rename.RenamePsiElementProcessor

// https://github.com/JetBrains/intellij-community/blob/idea/251.23774.435/plugins/properties/src/com/intellij/lang/properties/refactoring/rename/RenamePropertyProcessor.java
class NabuRenameProcessor : RenamePsiElementProcessor() {

    override fun canProcessElement(element: PsiElement): Boolean {
        return true
    }
}