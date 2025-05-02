package io.github.potjerodekool.nabuidea.language

import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.util.ProcessingContext

class NabuReferenceContributor : PsiReferenceContributor() {

    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        val referenceProvider: PsiReferenceProvider = object: PsiReferenceProvider() {
            override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<out PsiReference?> {
                return PsiReference.EMPTY_ARRAY
            }
        }

        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(),
            referenceProvider
        )
    }




}
