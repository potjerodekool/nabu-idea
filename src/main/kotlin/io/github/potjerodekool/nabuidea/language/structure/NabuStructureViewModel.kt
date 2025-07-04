package io.github.potjerodekool.nabuidea.language.structure

import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.Sorter
import com.intellij.openapi.editor.Editor
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuFileImpl

class NabuStructureViewModel(editor: Editor?,
                             nabuFile: NabuFileImpl,
                             root: StructureViewTreeElement) : StructureViewModelBase(nabuFile, editor,root) {

    override fun getSorters(): Array<out Sorter> {
        return arrayOf(Sorter.ALPHA_SORTER)
    }


}