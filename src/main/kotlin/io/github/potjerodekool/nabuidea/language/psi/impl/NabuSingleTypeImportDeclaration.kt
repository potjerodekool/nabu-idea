package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.lang.ASTNode
import io.github.potjerodekool.nabuidea.language.NabuTypes
import io.github.potjerodekool.nabuidea.language.psi.NabuImportDeclaration
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode

class NabuSingleTypeImportDeclaration(astNode: ASTNode) : ANTLRPsiNode(astNode), NabuImportDeclaration {
    override fun getImportText(): String? {
        val typeName = findChildByType<NabuTypeName>(NabuTypes.TYPE_NAME)
        return typeName?.text
    }
}