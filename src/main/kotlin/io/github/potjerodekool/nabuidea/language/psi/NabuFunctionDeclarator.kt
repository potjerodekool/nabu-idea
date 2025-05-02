package io.github.potjerodekool.nabuidea.language.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.util.PsiTreeUtil
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode

class NabuFunctionDeclarator(astNode: ASTNode) : ANTLRPsiNode(astNode) {

    fun identifier(): NabuIdentifier {
        return PsiTreeUtil.getChildOfType(
            this,
            NabuIdentifier::class.java
        )!!
    }
}