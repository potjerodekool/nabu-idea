package io.github.potjerodekool.nabuidea.language.structure

import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuFileImpl
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassBody
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassBodyDeclaration
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclarationWrapper
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassMemberDeclaration
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuFieldDeclaration
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuFunctionDeclaration
import io.github.potjerodekool.nabuidea.language.structure.SimplePsiTreeUtils.toTreeElements

class NabuFileStructureViewElement(element: NabuFileImpl) : NabuStructureViewElement<NabuFileImpl>(element) {

    override fun getChildren(): Array<out TreeElement?> {
        val compilationUnit = SimplePsiTreeUtils.getCompilationUnit(element)

        if (compilationUnit == null) {
            return emptyArray()
        }

        val topLevelDeclarations = SimplePsiTreeUtils.getTopLevelDeclarations(compilationUnit)

        return when (topLevelDeclarations.size) {
            1 -> {
                topLevelDeclarations
                    .flatMap {
                        getTopLevelChildren(it)
                    }.toTypedArray()
            }
            else -> {
                topLevelDeclarations
                    .map { it as NavigatablePsiElement }
                    .map { NabuStructureViewElementFactory.create(it) }
                    .toTypedArray()
            }
        }
    }

    fun getTopLevelChildren(topLevel: PsiElement): List<TreeElement> {
        val classBody = PsiTreeUtil.getChildOfType(
            topLevel,
            NabuClassBody::class.java
        )

        if (classBody == null) {
            return emptyList()
        }

        val children = PsiTreeUtil.getChildrenOfTypeAsList(
            classBody,
            NabuClassBodyDeclaration::class.java
        ).flatMap {
            PsiTreeUtil.getChildrenOfAnyType(
                it,
                NabuClassMemberDeclaration::class.java
            )
        }.flatMap {
            //TODO add NabuInterfaceDeclaration
            PsiTreeUtil.getChildrenOfAnyType(
                it,
                NabuFieldDeclaration::class.java,
                NabuFunctionDeclaration::class.java,
                NabuClassDeclarationWrapper::class.java
            )
        }

        return toTreeElements(children)
    }

}
