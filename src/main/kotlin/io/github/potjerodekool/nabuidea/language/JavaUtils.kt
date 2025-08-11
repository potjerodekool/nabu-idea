package io.github.potjerodekool.nabuidea.language

import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.project.Project
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope

object JavaUtils {

    fun findClassesByFilter(project: Project,
                            filter: (psiClass: PsiClass) -> Boolean): List<PsiClass> {
        val result = mutableListOf<PsiClass>()

        val virtualFiles = FileTypeIndex.getFiles(JavaFileType.INSTANCE, GlobalSearchScope.allScope(project))
        val psiManager = PsiManager.getInstance(project)

        virtualFiles.forEach { virtualFile ->
            val file = psiManager.findFile(virtualFile) as PsiJavaFile
            val classes = file.classes

            classes
                .filter(filter)
                .forEach { clazz ->
                    result.add(clazz)
            }
        }

        return result
    }
}