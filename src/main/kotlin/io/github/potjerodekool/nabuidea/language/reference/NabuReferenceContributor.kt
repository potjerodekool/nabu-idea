package io.github.potjerodekool.nabuidea.language.reference

import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiIdentifier
import com.intellij.psi.PsiJavaCodeReferenceElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceProvider
import com.intellij.psi.PsiReferenceRegistrar
import com.intellij.util.ProcessingContext
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuFileImpl
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuIdentifier
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuTypeIdentifier

class NabuReferenceContributor : PsiReferenceContributor() {

    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        val provider = NabuReferenceProvider()

        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(PsiJavaCodeReferenceElement::class.java),
            provider
        )

        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(NabuTypeIdentifier::class.java),
            provider
        )

                registrar.registerReferenceProvider(
                    PlatformPatterns.psiElement(NabuIdentifier::class.java),
                    provider
                )

        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(PsiJavaCodeReferenceElement::class.java),
            NabuPackageReferenceProvider()
        )

    }

}

class NabuReferenceProvider : PsiReferenceProvider() {
    override fun getReferencesByElement(
        element: PsiElement,
        context: ProcessingContext
    ): Array<out PsiReference?> {
        if (element is NabuTypeIdentifier) {
            var text = element.text

            if (text.endsWith("IntellijIdeaRulezzz")) {
                text = text.substring(0, text.length - "IntellijIdeaRulezzz".length)
            }

            val textRange = element.textRangeInParent

            return arrayOf<PsiReference>(NabuReference(element, textRange, text))
        } else if (element is NabuIdentifier) {
            var text = element.text

            if (text.endsWith("IntellijIdeaRulezzz")) {
                text = text.substring(0, text.length - "IntellijIdeaRulezzz".length)
            }

            val textRange = element.textRangeInParent

            return arrayOf<PsiReference>(NabuReference(element, textRange, text))
        } else if (element is NabuClassDeclaration && element.qualifiedName != null) {
            val text = element.qualifiedName!!
            val textRange = element.textRangeInParent
            return arrayOf<PsiReference>(NabuReference(element, textRange, text))
        } else if (element is NabuFileImpl) {
            var text = element.name
            text = text.substring(text.length - ".nabu".length)
            val textRange = element.textRangeInParent
            return arrayOf<PsiReference>(NabuReference(element, textRange, text))
        } else {

            return emptyArray()
        }
    }
}

class NabuPackageReferenceProvider : PsiReferenceProvider() {

    override fun getReferencesByElement(
        element: PsiElement,
        context: ProcessingContext
    ): Array<out PsiReference?> {
        val text = element.text

        return emptyArray()
    }

}