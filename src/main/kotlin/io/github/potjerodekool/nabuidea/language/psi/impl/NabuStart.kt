package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode
import org.antlr.intellij.adaptor.psi.ScopeNode

class NabuStart(astNode: ASTNode) : ANTLRPsiNode(astNode), ScopeNode {

    override fun resolve(element: PsiNamedElement): PsiElement? {
        TODO("Not yet implemented")
    }

    fun findCompilationUnit(): NabuCompilationUnit? {
        val wrapper = findChildByClass(NabuCompilationUnitWrapper::class.java)
        return wrapper?.findCompilationUnit()
    }

}