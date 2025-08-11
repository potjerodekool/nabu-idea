package io.github.potjerodekool.nabuidea.language

import com.intellij.lang.ASTNode
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiClass
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import io.github.potjerodekool.nabuidea.NabuFileType
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuNamedElement

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