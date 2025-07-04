package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import io.github.potjerodekool.nabuidea.language.NabuTypes
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode
import org.antlr.intellij.adaptor.psi.ScopeNode

class NabuCompilationUnit(astNode: ASTNode,
                          val isModular: Boolean) : ANTLRPsiNode(astNode), ScopeNode {

    override fun resolve(element: PsiNamedElement?): PsiElement? {
        TODO("Not yet implemented")
    }

    fun findPackageDeclaration(): NabuPackageDeclaration? {
        if (isModular) {
            return null
        }

        return findChildByType(NabuTypes.PACKAGE_DECLARATION)
    }

}