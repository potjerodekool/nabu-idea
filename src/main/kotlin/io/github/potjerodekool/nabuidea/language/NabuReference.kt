package io.github.potjerodekool.nabuidea.language

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult

class NabuReference(element: PsiElement, textRange: TextRange) : PsiPolyVariantReferenceBase<PsiElement>(
    element, textRange) {

    override fun multiResolve(p0: Boolean): Array<out ResolveResult?> {
        return arrayOf()
    }
}