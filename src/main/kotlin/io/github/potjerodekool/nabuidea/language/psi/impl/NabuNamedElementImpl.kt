package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.potjerodekool.nabuidea.language.psi.NabuNamedElement

abstract class NabuNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), NabuNamedElement  {
}
