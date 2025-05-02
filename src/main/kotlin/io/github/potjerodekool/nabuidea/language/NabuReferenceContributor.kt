package io.github.potjerodekool.nabuidea.language

import com.intellij.patterns.ElementPattern
import com.intellij.patterns.ElementPatternCondition
import com.intellij.patterns.IElementTypePattern
import com.intellij.patterns.PlatformPatterns
import com.intellij.patterns.PsiElementPattern
import com.intellij.patterns.PsiExpressionPattern
import com.intellij.patterns.TreeElementPattern
import com.intellij.patterns.uast.injectionHostUExpression
import com.intellij.platform.diagnostic.telemetry.PlatformMetrics
import com.intellij.psi.*
import com.intellij.util.ProcessingContext
import org.jetbrains.uast.UElement

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

        // org.jetbrains.uast.UElement, com.intellij.util.ProcessingContext

        /*
            { element : UElement, context : ProcessingContext ->
                return@registerUastReferenceProvider true
            }
         */

    }




}

class MyProvider : UastReferenceProvider() {
    override fun getReferencesByElement(
        element: UElement,
        context: ProcessingContext
    ): Array<PsiReference> {
        return emptyArray()
    }

}
