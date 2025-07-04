package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.openapi.util.NlsSafe
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry
import io.github.potjerodekool.nabuidea.language.NabuPsiUtils
import io.github.potjerodekool.nabuidea.language.NabuTypes
import io.github.potjerodekool.nabuidea.language.psi.util.NabuElementFactory
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode

class NabuClassDeclarationWrapper(astNode: ASTNode) : ANTLRPsiNode(astNode), NabuNamedElement,
    NavigatablePsiElement {

    override fun setName(newName: @NlsSafe String): PsiElement? {
        NabuPsiUtils.changeName(
            this,
            {

                val newIdentifier = NabuElementFactory.createTypeIdentifier(
                    node.psi.project,
                    newName
                )
                return@changeName newIdentifier.node
            }
        )

        return this;
    }

    override fun getNamedIdentifierNode(): ASTNode? {
        val normalClassDeclarationNode = node.findChildByType(NabuTypes.NORMAL_CLASS_DECLARATION)

        if (normalClassDeclarationNode == null) {
            return null
        }

        return normalClassDeclarationNode
    }

    override fun getNameIdentifier(): PsiElement? {
        val namedIdentifierNode = getNamedIdentifierNode()
        return namedIdentifierNode?.psi
    }

    override fun getReferences(): Array<out PsiReference?> {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this)
    }
}