package io.github.potjerodekool.nabuidea.language

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import io.github.potjerodekool.nabuidea.NabuFileType
import io.github.potjerodekool.nabuidea.language.psi.NabuFile
import io.github.potjerodekool.nabuidea.language.psi.NabuNamedElement
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

        try {
            val virtualFiles = FileTypeIndex.getFiles(NabuFileType, GlobalSearchScope.allScope(project))

            virtualFiles.forEach { virtualFile ->
                val nabuFile = PsiManager.getInstance(project).findFile(virtualFile) as NabuFile?

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
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }
}