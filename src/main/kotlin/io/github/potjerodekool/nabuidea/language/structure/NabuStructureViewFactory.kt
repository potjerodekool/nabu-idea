package io.github.potjerodekool.nabuidea.language.structure

import com.intellij.ide.structureView.StructureViewBuilder
import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder
import com.intellij.lang.PsiStructureViewFactory
import com.intellij.openapi.editor.Editor
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiFile
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuFileImpl

class NabuStructureViewFactory : PsiStructureViewFactory {
    override fun getStructureViewBuilder(psiFile: PsiFile): StructureViewBuilder? {
        return object : TreeBasedStructureViewBuilder() {
            override fun createStructureViewModel(editor: Editor?): StructureViewModel {
                val nabuFile = psiFile as NabuFileImpl
                val cu = SimplePsiTreeUtils.getCompilationUnit(nabuFile)
                val topLevels =
                    SimplePsiTreeUtils.getTopLevelDeclarations(cu)

                if (topLevels.size == 1) {
                    val topLevel = topLevels[0] as NavigatablePsiElement
                    return NabuStructureViewModel(
                        editor,
                        nabuFile,
                        NabuTopLevelStructureViewElement(topLevel)
                    )
                } else {
                    return NabuStructureViewModel(
                        editor,
                        nabuFile,
                        NabuFileStructureViewElement(nabuFile)
                    )
                }
            }
        }
    }

}