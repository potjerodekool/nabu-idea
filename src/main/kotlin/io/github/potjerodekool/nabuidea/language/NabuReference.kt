package io.github.potjerodekool.nabuidea.language

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult

class NabuReference(element: PsiElement, textRange: TextRange) : PsiPolyVariantReferenceBase<PsiElement>(element, textRange) {

    private val name = element.text
        .substring(textRange.startOffset, textRange.endOffset)

    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult?> {
        val project = myElement.project

        return arrayOf()
    }

    override fun getVariants(): Array<out Any?> {
        return super.getVariants()
    }
}