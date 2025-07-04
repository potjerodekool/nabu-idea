package io.github.potjerodekool.nabuidea.language

import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.util.ProcessingContext
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration


class NabuReferenceContributor : PsiReferenceContributor() {

    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(PsiElement::class.java),
            NabuReferenceProvider()
        )

        /*
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(PsiJavaCodeReferenceElement::class.java)
                .withParent(PsiImportStatement::class.java),
            NabuReferenceProvider()
        )
        */
    }




}

class NabuReferenceProvider : PsiReferenceProvider() {
    override fun getReferencesByElement(
        element: PsiElement,
        context: ProcessingContext
    ): Array<out PsiReference?> {
        if (element !is NabuClassDeclaration) {
            return emptyArray()
        }

        val refElement = element

        val qualifiedName: String? = refElement.qualifiedName

        if (qualifiedName == null) {
            return PsiReference.EMPTY_ARRAY
        }

        return arrayOf<PsiReference>(NabuReference(element))
    }

}

class NabuImportPsiReference(element: PsiJavaCodeReferenceElement) : PsiReferenceBase<PsiJavaCodeReferenceElement>(element, element.textRangeInParent) {

    override fun resolve(): PsiElement? {
        val qualifiedName = myElement.qualifiedName

        if (qualifiedName == null) {
            return null
        }

        return NabuClassIndex.iNSTANCE.find(qualifiedName, myElement.project).stream()
            .findFirst()
            .orElse(null)
    }

    override fun getVariants(): Array<Any?> {
        // Optional: provide autocomplete/import suggestions
        //return MyLangIndexUtil.getAllQualifiedNames(myElement.getProject()).toArray()
        TODO()
    }

}