package io.github.potjerodekool.nabuidea.language.structure

import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import io.github.potjerodekool.nabuidea.language.psi.NabuClassBody
import io.github.potjerodekool.nabuidea.language.psi.NabuClassBodyDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuClassDeclarationWrapper
import io.github.potjerodekool.nabuidea.language.psi.NabuClassMemberDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuCompilationUnitWrapper
import io.github.potjerodekool.nabuidea.language.psi.NabuFieldDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuFile
import io.github.potjerodekool.nabuidea.language.psi.NabuFunctionDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuClassDeclaration
import io.github.potjerodekool.nabuidea.language.psi.NabuCompilationUnit
import io.github.potjerodekool.nabuidea.language.psi.NabuStart
import io.github.potjerodekool.nabuidea.language.psi.NabuTopLevelClassOrInterfaceDeclaration
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode

object SimplePsiTreeUtils {

    fun getCompilationUnit(file: NabuFile): PsiElement? {
        val start = PsiTreeUtil.getChildOfType(
            file,
            NabuStart::class.java
        )

        if (start == null) {
            return null
        }

        val compilationUnit = PsiTreeUtil.getChildOfType(
            start,
            NabuCompilationUnitWrapper::class.java
        )

        if (compilationUnit == null) {
            return null
        }

        //TODO add modular
        return PsiTreeUtil.getChildOfAnyType(
            compilationUnit,
            NabuCompilationUnit::class.java,
        )
    }

    fun getTopLevelDeclarations(compilationUnit: PsiElement?): List<PsiElement> {
        if (compilationUnit == null) {
            return emptyList()
        }

        val topLevels = PsiTreeUtil.getChildrenOfTypeAsList<ANTLRPsiNode>(
            compilationUnit,
            NabuTopLevelClassOrInterfaceDeclaration::class.java
        )

        return topLevels.map { topLevel ->
            PsiTreeUtil.getChildOfAnyType(
                topLevel,
                NabuClassDeclarationWrapper::class.java
            )
        }.map {
            //TODO add others
            PsiTreeUtil.getChildOfAnyType(
                it,
                NabuClassDeclaration::class.java
            ) as PsiElement
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

    /*
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
                NabuClassMemberDeclaration::class.java,
                NabuInstanceInitializer::class.java,
                NabuStaticInitializer::class.java,
                NabuConstructorDeclaration::class.java
            )
        }.flatMap {
            PsiTreeUtil.getChildrenOfAnyType(
                it,
                NabuFieldDeclaration::class.java,
                NabuFunctionDeclaration::class.java,
                NabuClassDeclarationWrapper::class.java,
                NabuInterfaceDeclaration::class.java
            )
        }

        return toTreeElements(children)
    }

     */

    fun toTreeElements(list: List<PsiElement>): List<TreeElement> {
        return list
            .map { NabuStructureViewElementFactory.create(it as NavigatablePsiElement) }
    }
}