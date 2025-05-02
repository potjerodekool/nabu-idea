package io.github.potjerodekool.nabuidea.language.structure

import com.intellij.icons.AllIcons
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import com.intellij.ui.RowIcon
import io.github.potjerodekool.nabuidea.language.NabuTypes
import javax.swing.Icon

object IconUtils {

    fun getModifierIcon(modifiers: List<PsiElement>,
                        elementType: ElementType): Icon? {
        if (modifiers.isEmpty()) {
            return null
        }

        return modifiers.map<PsiElement, Icon?> {
            val firstChild = it.firstChild

            if (firstChild == null) {
                return null
            }

            return when(firstChild.elementType) {
                NabuTypes.PUBLIC -> AllIcons.Nodes.Public
                NabuTypes.PROTECTED -> AllIcons.Nodes.Protected
                NabuTypes.PRIVATE -> AllIcons.Nodes.Private
                NabuTypes.ABSTRACT -> {
                    if (elementType == ElementType.CLASS) {
                        AllIcons.Nodes.AbstractClass
                    } else if (elementType == ElementType.FUNCTION) {
                        AllIcons.Nodes.AbstractMethod
                    } else {
                        null
                    }
                }
                NabuTypes.STATIC -> AllIcons.Nodes.Static
                else -> null
            }
        }.reduce({ a,b ->
            RowIcon(a,b)
        })
    }
}