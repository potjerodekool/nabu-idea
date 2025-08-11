package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode

class NabuIdentifier(astNode: ASTNode) : ANTLRPsiNode(astNode) {

    override fun getReferences(): Array<out PsiReference?> {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this)
    }

}