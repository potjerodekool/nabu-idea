package io.github.potjerodekool.nabuidea.language.reference

import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.findDirectory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager

object NabuReferenceUtils {

    fun findPackage(
        packageName: String,
        project: Project
    ): PsiElement? {
        val path = packageName.replace('.', '/')

        val roots = ProjectRootManager.getInstance(project).contentSourceRoots

        val dir = roots
            .map { root -> root.findDirectory(path) }
            .filterNotNull()
            .firstOrNull()

        return if (dir != null)
            PsiManager.getInstance(project).findDirectory(dir);
        else null
    }
}