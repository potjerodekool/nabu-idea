package io.github.potjerodekool.nabuidea.search

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.search.SearchScope
import com.intellij.psi.search.searches.ClassInheritorsSearch
import com.intellij.util.EmptyQuery
import com.intellij.util.Query
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration
import io.github.potjerodekool.nabuidea.language.psi.util.ToPsi

interface DeclarationSearchRequest<in T> {
    val project: Project
    val searchScope: SearchScope
}

interface SearchRequestWithElement<T : PsiElement> : DeclarationSearchRequest<T> {
    val originalElement: T
    override val project: Project get() = originalElement.project
}

class HierarchySearchRequest<T : PsiElement>(
    override val originalElement: T,
    override val searchScope: SearchScope,
    val searchDeeply: Boolean = true
) : SearchRequestWithElement<T> {
    fun <U : PsiElement> copy(newOriginalElement: U): HierarchySearchRequest<U> =
        HierarchySearchRequest(newOriginalElement, searchScope, searchDeeply)

    fun searchInheritors(): Query<PsiClass> {
        val psiClass: PsiClass = when (originalElement) {
            is NabuClassDeclaration -> ToPsi.toPsiClass(originalElement)
            is PsiClass -> originalElement
            else -> null
        } ?: return EmptyQuery.getEmptyQuery()

        return ClassInheritorsSearch.search(
            psiClass,
            searchScope,
            searchDeeply,
            true,
            true
        )
    }

}