package io.github.potjerodekool.nabuidea.language.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.NlsSafe
import com.intellij.psi.PsiElement
import io.github.potjerodekool.nabuidea.language.NabuTypes

class NabuConstructorDeclaration(astNode: ASTNode) : ASTWrapperPsiElement(astNode), NabuNamedElement {

    override fun getNameIdentifier(): PsiElement? {
        val constructorDeclarator = node.findChildByType(NabuTypes.CONSTRUCTOR_DECLARATOR)

        if (constructorDeclarator == null) {
            return null
        }

        val simpleTypeName = constructorDeclarator.findChildByType(
            NabuTypes.SIMPLE_TYPE_NAME
        )

        if (simpleTypeName == null) {
            return null
        }

        val typeIdentifier = simpleTypeName.findChildByType(
            NabuTypes.TYPE_IDENTIFIER
        )

        return typeIdentifier?.psi
    }

    override fun setName(p0: @NlsSafe String): PsiElement? {
        TODO("Not yet implemented")
    }

}