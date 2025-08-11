package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.lang.ASTNode
import io.github.potjerodekool.nabuidea.language.psi.NabuImportDeclaration
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode

class NabuStaticImportOnDemandDeclaration(astNode: ASTNode) : ANTLRPsiNode(astNode), NabuImportDeclaration {
    override fun getImportText(): String? {
        return null
    }
}