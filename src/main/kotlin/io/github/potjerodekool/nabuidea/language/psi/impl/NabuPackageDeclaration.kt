package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.openapi.util.NlsSafe
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiQualifiedNamedElement
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode

class NabuPackageDeclaration(astNode: ASTNode) : ANTLRPsiNode(astNode), PsiQualifiedNamedElement {
    override fun getQualifiedName(): String? {
        return this.findChildrenByClass(NabuIdentifier::class.java)
            .joinToString(separator = ".") { it -> it.firstChild.text }
    }

    override fun setName(name: @NlsSafe String): PsiElement? {
        TODO("Not yet implemented")
    }

}
