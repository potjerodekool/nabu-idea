package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.util.PsiTreeUtil
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode

class NabuFunctionHeader(astNode: ASTNode) : ANTLRPsiNode(astNode) {

    fun functionDeclarator(): NabuFunctionDeclarator {
        return PsiTreeUtil.getChildOfType(
            this,
            NabuFunctionDeclarator::class.java
        )!!
    }
}