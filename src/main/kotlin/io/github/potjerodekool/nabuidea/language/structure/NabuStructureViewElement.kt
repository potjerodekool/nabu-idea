package io.github.potjerodekool.nabuidea.language.structure

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.NavigatablePsiElement

abstract class NabuStructureViewElement<E : NavigatablePsiElement>(val element: E) : StructureViewTreeElement,
    SortableTreeElement {

    override fun getValue(): Any? {
        return element
    }

    override fun navigate(requestFocus: Boolean) {
        element.navigate(requestFocus)
    }

    override fun canNavigate(): Boolean {
        return element.canNavigate()
    }

    override fun canNavigateToSource(): Boolean {
        return element.canNavigateToSource()
    }

    override fun getAlphaSortKey(): String {
        val name: String? = element.name
        return name ?: ""
    }

    override fun getPresentation(): ItemPresentation {
        val presentation: ItemPresentation? = element.presentation
        return presentation ?: PresentationData()
    }

}