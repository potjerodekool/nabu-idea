package io.github.potjerodekool.nabuidea.language.reference;

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase

class NabuPackageReference(element: PsiElement,
    textRange: TextRange,
    private val packageName: String ) : PsiReferenceBase<PsiElement>(element, textRange) {
    override fun resolve(): PsiElement? {
        return NabuReferenceUtils.findPackage(packageName, element.project)
    }

    override fun getVariants(): Array<out Any?> {
        return emptyArray()
    }
}
