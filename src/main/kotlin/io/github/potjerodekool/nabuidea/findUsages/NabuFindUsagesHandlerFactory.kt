package io.github.potjerodekool.nabuidea.findUsages

import com.intellij.find.findUsages.FindUsagesHandler
import com.intellij.find.findUsages.FindUsagesHandlerFactory
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration

class NabuFindUsagesHandlerFactory(project: Project) : FindUsagesHandlerFactory() {

    val findClassOptions = NabuClassFindUsagesOptions(project)

    override fun canFindUsages(element: PsiElement): Boolean {
        return NabuFindUsagesProvider().canFindUsagesFor(element)
    }

    override fun createFindUsagesHandler(
        element: PsiElement,
        forHighlightUsages: Boolean
    ): FindUsagesHandler? {
        if (element is NabuClassDeclaration) {
            return NabuFindClassUsagesHandler(
                element,
                this,
                emptyList()
            )
        } else {
            return null
        }
    }
}