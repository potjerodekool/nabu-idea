package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner

interface NabuNamedElement : PsiNameIdentifierOwner, PsiElement {

    fun getNamedIdentifierNode(): ASTNode? {
        return null
    }
}