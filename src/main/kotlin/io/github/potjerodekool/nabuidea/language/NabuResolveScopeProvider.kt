package io.github.potjerodekool.nabuidea.language

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.psi.ResolveScopeProvider
import com.intellij.psi.search.GlobalSearchScope
import io.github.potjerodekool.nabuidea.NabuLanguage

class NabuResolveScopeProvider : ResolveScopeProvider() {

    override fun getResolveScope(file: VirtualFile,
                                 project: Project): GlobalSearchScope? {
        val fileType = file.fileType

        if (fileType is LanguageFileType) {
            if (fileType.language == NabuLanguage) {
                val index =
                    if (project.isDefault) null
                    else ProjectRootManager.getInstance(project).fileIndex

                if (index == null) {
                    return GlobalSearchScope.fileScope(project, file)
                }

                if (index.isInContent(file) && !index.isInSource(file)) {
                    val psi = PsiManager.getInstance(project).findFile(file)

                    if (psi == null) {
                        return GlobalSearchScope.fileScope(project, file)
                    }
                }
                return null
            } else {
                return null
            }
        }

        return null
    }
}