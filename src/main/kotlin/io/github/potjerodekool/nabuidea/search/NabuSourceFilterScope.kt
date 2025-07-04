package io.github.potjerodekool.nabuidea.search

import com.intellij.ide.highlighter.JavaClassFileType
import com.intellij.openapi.fileTypes.FileTypeRegistry
import com.intellij.openapi.roots.FileIndexFacade
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.search.DelegatingGlobalSearchScope
import com.intellij.psi.search.GlobalSearchScope

class NabuSourceFilterScope(scope: GlobalSearchScope, private val includeLibrarySources: Boolean = false)
    : DelegatingGlobalSearchScope(scope) {

        private val index: FileIndexFacade?

        init {
            val project = this.project
            this.index = if (project == null) null else FileIndexFacade.getInstance(project);
        }

    override fun contains(file: VirtualFile): Boolean {
        if (!super.contains(file)
            || this.index == null) {
            return false
        } else if (FileTypeRegistry.getInstance().isFileOfType(
            file,
                JavaClassFileType.INSTANCE)) {
            return false
        } else {
            //TODO See JavaSourceFilterScope
            return false
        }
    }
}