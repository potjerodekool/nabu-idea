package io.github.potjerodekool.nabuidea.language

import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator
import com.intellij.psi.PsiElement

class NabuManipulator : AbstractElementManipulator<PsiElement>() {
    override fun handleContentChange(
        element: PsiElement,
        range: TextRange,
        newContent: String?
    ): PsiElement? {
        TODO("Not yet implemented")
    }
}