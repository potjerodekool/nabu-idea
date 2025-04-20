package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiElement
import io.github.potjerodekool.nabuidea.language.psi.NabuElementFactory
import io.github.potjerodekool.nabuidea.language.psi.NabuProperty
import io.github.potjerodekool.nabuidea.language.psi.NabuTypes
import javax.swing.Icon

object NabuPsiImplUtil {

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
