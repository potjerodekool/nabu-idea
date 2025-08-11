package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.openapi.util.NlsSafe
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiQualifiedNamedElement
import com.intellij.psi.PsiReference
import io.github.potjerodekool.nabuidea.language.reference.NabuPackageReference
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode

class NabuPackageDeclaration(astNode: ASTNode) : ANTLRPsiNode(astNode), PsiQualifiedNamedElement {
    override fun getQualifiedName(): String? {
        return this.findChildrenByClass(NabuIdentifier::class.java)
            .joinToString(separator = ".") { it -> it.firstChild.text }
    }

    override fun setName(name: @NlsSafe String): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun getReference(): PsiReference? {
        val packageName = qualifiedName

        if (packageName == null) {
            return null
        }

        return NabuPackageReference(
            this,
            textRange,
            packageName
        )
    }

}
