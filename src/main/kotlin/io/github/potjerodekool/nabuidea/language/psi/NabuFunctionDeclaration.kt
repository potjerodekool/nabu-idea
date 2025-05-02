package io.github.potjerodekool.nabuidea.language.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.util.PsiTreeUtil
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode
import org.antlr.intellij.adaptor.psi.ScopeNode

class NabuFunctionDeclaration(astNode: ASTNode) : ANTLRPsiNode(astNode), ScopeNode {

    fun functionModifiers(): List<NabuModifier> {
        return PsiTreeUtil.getChildrenOfTypeAsList(
            this,
            NabuModifier::class.java
        );
    }

    fun functionHeader(): NabuFunctionHeader {
        return PsiTreeUtil.getChildOfType(
            this,
            NabuFunctionHeader::class.java
        )!!
    }

    override fun resolve(element: PsiNamedElement?): PsiElement? {
        TODO("Not yet implemented")
    }
}