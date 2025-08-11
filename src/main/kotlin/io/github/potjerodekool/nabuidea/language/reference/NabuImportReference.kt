package io.github.potjerodekool.nabuidea.language.reference

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import io.github.potjerodekool.nabuidea.language.JavaUtils
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuImportDeclaration

class NabuImportReference(element: NabuImportDeclaration,
                          textRange: TextRange) : PsiReferenceBase<NabuImportDeclaration>(element, textRange) {

    override fun resolve(): PsiElement? {
        val text = element.getImportText()

        val project = element.project

        val classes : List<PsiClass> = JavaUtils.findClassesByFilter(project, { psiClass ->
            psiClass.qualifiedName.equals(text)
        })

        return if (classes.size == 1) classes[0]
            else null
    }
}