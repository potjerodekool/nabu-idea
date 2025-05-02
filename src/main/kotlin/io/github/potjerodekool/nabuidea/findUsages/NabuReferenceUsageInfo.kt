package io.github.potjerodekool.nabuidea.findUsages

import com.intellij.psi.PsiReference
import com.intellij.usageView.UsageInfo

class NabuReferenceUsageInfo(reference: PsiReference) : UsageInfo(reference) {
    private val referenceType = reference::class.java

    override fun getReference(): PsiReference? {
        val element = element ?: return null
        return element.references.singleOrNull { it::class.java == referenceType }
    }
}

class NabuReferencePreservingUsageInfo(private val reference: PsiReference) : UsageInfo(reference) {
    override fun getReference() = reference
}