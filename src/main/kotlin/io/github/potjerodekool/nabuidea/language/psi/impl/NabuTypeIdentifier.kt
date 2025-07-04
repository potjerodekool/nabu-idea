package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiIdentifier
import com.intellij.psi.tree.IElementType
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode

class NabuTypeIdentifier(astNode: ASTNode) : ANTLRPsiNode(astNode), PsiIdentifier {

    override fun clone(): Any {
        TODO("Not yet implemented")
    }

    override fun getTokenType(): IElementType? {
        return NabuTokenType.IDENTIFIER
    }
}