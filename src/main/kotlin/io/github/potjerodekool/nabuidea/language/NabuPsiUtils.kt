package io.github.potjerodekool.nabuidea.language

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import io.github.potjerodekool.nabuidea.language.psi.NabuNamedElement
import io.github.potjerodekool.nabuidea.language.psi.NabuPsiClass

object NabuPsiUtils {

    fun changeName(element: NabuNamedElement,
                   newIdentifierProvider: () -> ASTNode): Boolean {
        val currentNameIdentifierNode = element.getNamedIdentifierNode()

        if (currentNameIdentifierNode != null) {
            val newChild = newIdentifierProvider.invoke()

            element.node.replaceChild(
                currentNameIdentifierNode,
                newChild)
            return true
        } else {
            return false
        }
    }

    fun getTopLevelClass(element: PsiClass): PsiClass? {
        val parent = element.parent
        return if (parent is PsiClass) getTopLevelClass(parent)
                else element
    }
}