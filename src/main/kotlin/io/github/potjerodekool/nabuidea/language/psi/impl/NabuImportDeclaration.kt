package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.util.elementType
import io.github.potjerodekool.nabuidea.language.NabuTypes
import io.github.potjerodekool.nabuidea.language.psi.NabuImportDeclaration
import io.github.potjerodekool.nabuidea.language.reference.NabuImportReference
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode

open class NabuImportDeclaration(astNode: ASTNode) : ANTLRPsiNode(astNode), PsiElement {

    fun getImportKind(): ImportKind? {
        val elementType = firstChild.elementType

        if (elementType == null) {
            return null
        }

        return when (elementType) {
            NabuTypes.SINGLE_TYPE_IMPORT -> ImportKind.SINGLE_TYPE
            NabuTypes.TYPE_IMPORT_ON_DEMAND -> ImportKind.TYPE_ON_DEMAND
            NabuTypes.SINGLE_STATIC_IMPORT -> ImportKind.SINGLE_STATIC
            NabuTypes.STATIC_IMPORT_ON_DEMAND -> ImportKind.STATIC_ON_DEMAND
            else -> null
        }
    }

    fun getImportText(): String? {
        val child = firstChild as NabuImportDeclaration
        return child.getImportText()
    }

    enum class ImportKind {
        SINGLE_TYPE,
        TYPE_ON_DEMAND,
        SINGLE_STATIC,
        STATIC_ON_DEMAND
    }

    override fun getReference(): PsiReference? {
        return NabuImportReference(this, textRange)
    }
}