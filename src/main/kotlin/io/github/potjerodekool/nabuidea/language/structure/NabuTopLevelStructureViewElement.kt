package io.github.potjerodekool.nabuidea.language.structure

import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.NavigatablePsiElement
import io.github.potjerodekool.nabuidea.language.structure.DefaultStructureViewElement.Companion.NO_PRESENTATION

class NabuTopLevelStructureViewElement(element: NavigatablePsiElement) : NabuStructureViewElement<NavigatablePsiElement>(element) {

    companion object {
        private fun resolvePresentation(element: NavigatablePsiElement): ItemPresentation {
            return element.presentation ?: NO_PRESENTATION
        }
    }

    override fun getChildren(): Array<out TreeElement?> {
        return SimplePsiTreeUtils.getTopLevelChildren(element)
            .toTypedArray()
    }

    override fun getPresentation(): ItemPresentation {
        return resolvePresentation(element)
    }
}
