package io.github.potjerodekool.nabuidea.language.structure

import com.intellij.icons.AllIcons
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.util.NlsSafe
import com.intellij.psi.NavigatablePsiElement
import com.intellij.ui.RowIcon
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuFunctionDeclaration
import io.github.potjerodekool.nabuidea.language.structure.IconUtils.getModifierIcon
import javax.swing.Icon

class DefaultStructureViewElement<E : NavigatablePsiElement>(element: E) : NabuStructureViewElement<E>(element) {

    companion object {
        val NO_PRESENTATION = object : ItemPresentation {
            override fun getPresentableText(): @NlsSafe String? {
                return null
            }

            override fun getIcon(p0: Boolean): Icon? {
                return null
            }
        }
    }

    private val presentation = when(element) {
        is NabuFunctionDeclaration -> FunctionItemPresentation(element)
        else -> NO_PRESENTATION
    }

    override fun getChildren(): Array<out TreeElement?> {
        return emptyArray()
    }

    override fun getPresentation(): ItemPresentation {
        return presentation
    }

}

class FunctionItemPresentation(val function: NabuFunctionDeclaration) : ItemPresentation {

    override fun getPresentableText(): @NlsSafe String? {
        return function.functionHeader().functionDeclarator().identifier().text
    }

    override fun getIcon(p0: Boolean): Icon? {
        val modifiers = function.functionModifiers()
        val modifierIcon = getModifierIcon(modifiers, ElementType.FUNCTION)

        return if (modifierIcon != null) RowIcon(modifierIcon, AllIcons.Nodes.Method)
            else AllIcons.Nodes.Method
    }

}