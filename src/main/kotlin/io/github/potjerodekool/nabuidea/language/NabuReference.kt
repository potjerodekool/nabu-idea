package io.github.potjerodekool.nabuidea.language

import com.intellij.openapi.util.TextRange
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult
import com.intellij.psi.search.GlobalSearchScope
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration


class NabuReference(element: PsiElement/*, textRange: TextRange*/) : PsiPolyVariantReferenceBase<PsiElement>(element, TextRange.from(0, element.textLength)) {
/*
    private val name = element.text
        .substring(textRange.startOffset, textRange.endOffset)
    */

    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult?> {
        val project = myElement.project
        //return arrayOf()
        TODO()
    }

    override fun resolve(): PsiElement? {
        //val qualifiedName = myElement.name
        var classDeclaration = myElement as NabuClassDeclaration

        classDeclaration as NavigatablePsiElement

        val qualifiedName = classDeclaration.qualifiedName

        return classDeclaration
        /*
        return NabuClassIndex()
            .get(qualifiedName, myElement.getProject(), GlobalSearchScope.allScope(myElement.getProject()))
            .stream().findFirst().orElse(null)
        */
    }

    override fun getVariants(): Array<out Any?> {
        return super.getVariants()
    }
}