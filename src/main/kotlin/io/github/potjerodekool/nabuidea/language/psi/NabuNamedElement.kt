package io.github.potjerodekool.nabuidea.language.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiNameIdentifierOwner

interface NabuNamedElement : PsiNameIdentifierOwner {

    fun getNamedIdentifierNode(): ASTNode? {
        return null
    }
}