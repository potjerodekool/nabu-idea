package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiElement
import io.github.potjerodekool.nabuidea.language.psi.NabuElementFactory
import io.github.potjerodekool.nabuidea.language.psi.NabuProperty
import io.github.potjerodekool.nabuidea.language.psi.NabuTypes
import javax.swing.Icon

object NabuPsiImplUtil {
    fun getKey(element: NabuProperty): String? {
        val keyNode = element.getNode().findChildByType(NabuTypes.KEY)
        if (keyNode != null) {
            // IMPORTANT: Convert embedded escaped spaces to simple spaces
            return keyNode.getText().replace("\\\\ ".toRegex(), " ")
        } else {
            return null
        }
    }

    fun getValue(element: NabuProperty): String? {
        val valueNode = element.getNode().findChildByType(NabuTypes.VALUE)
        if (valueNode != null) {
            return valueNode.getText()
        } else {
            return null
        }
    }

    fun getName(element: NabuProperty): String? {
        return getKey(element)
    }

    fun setName(element: NabuProperty, newName: String): PsiElement {
        val keyNode = element.getNode().findChildByType(NabuTypes.KEY)
        if (keyNode != null) {
            val property = NabuElementFactory.createProperty(element.getProject(), newName)
            val newKeyNode = property.getFirstChild().getNode()
            element.getNode().replaceChild(keyNode, newKeyNode)
        }
        return element
    }

    fun getNameIdentifier(element: NabuProperty): PsiElement? {
        val keyNode = element.getNode().findChildByType(NabuTypes.KEY)
        if (keyNode != null) {
            return keyNode.getPsi()
        } else {
            return null
        }
    }

    fun getPresentation(element: NabuProperty): ItemPresentation {
        return object : ItemPresentation {
            override fun getPresentableText(): String? {
                //return element.getKey();
                throw UnsupportedOperationException()
            }

            override fun getLocationString(): String? {
                val containingFile = element.getContainingFile()
                return if (containingFile == null) null else containingFile.getName()
            }

            override fun getIcon(unused: Boolean): Icon? {
                return element.getIcon(0)
            }
        }
    }
}
