package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.potjerodekool.nabuidea.language.NabuTypes
import io.github.potjerodekool.nabuidea.language.psi.NabuImportDeclaration

class NabuSingleStaticImportDeclaration(astNode: ASTNode) : ASTWrapperPsiElement(astNode), NabuImportDeclaration {

    override fun getImportText(): String? {
        val typeName = findChildByType<NabuTypeName>(NabuTypes.TYPE_NAME)
        return typeName?.text
    }


}