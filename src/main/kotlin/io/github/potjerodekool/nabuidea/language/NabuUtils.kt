package io.github.potjerodekool.nabuidea.language

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import io.github.potjerodekool.nabuidea.NabuFileType
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuFileImpl
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuNamedElement
import io.github.potjerodekool.nabuidea.language.structure.SimplePsiTreeUtils

object NabuUtils {

    fun findClasses(project: Project,
                    name: String): List<NabuNamedElement> {
        return findClassesByFilter(project, { element -> name == element.name})
    }

    fun findClasses(project: Project): List<NabuNamedElement> {
        return findClassesByFilter(project, { true })
    }

    fun findClassesByFilter(project: Project, filter: (NabuNamedElement) -> Boolean): List<NabuNamedElement> {
        val result = mutableListOf<NabuNamedElement>()

        val virtualFiles = FileTypeIndex.getFiles(NabuFileType, GlobalSearchScope.allScope(project))
        val psiManager = PsiManager.getInstance(project)

        virtualFiles.forEach { virtualFile ->
            val nabuFile = psiManager.findFile(virtualFile) as NabuFileImpl?

            if (nabuFile != null) {
                val compilationUnit = SimplePsiTreeUtils.getCompilationUnit(nabuFile)
                val topLevels = SimplePsiTreeUtils.getTopLevelDeclarations(compilationUnit);

                topLevels
                    .map { topLevel -> topLevel as NabuNamedElement }
                    .forEach { topLevel ->
                        if (filter.invoke(topLevel)) {
                            result.add(topLevel)
                        }
                    }
            }
        }

        return result
    }
}