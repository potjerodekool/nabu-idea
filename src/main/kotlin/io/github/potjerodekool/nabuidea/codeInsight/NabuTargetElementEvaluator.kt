package io.github.potjerodekool.nabuidea.codeInsight

import com.intellij.codeInsight.TargetElementEvaluatorEx2
import com.intellij.codeInsight.TargetElementUtilExtender
import com.intellij.psi.PsiElement
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuPsiClass

class NabuTargetElementEvaluator : TargetElementEvaluatorEx2(), TargetElementUtilExtender {

    override fun getAllAdditionalFlags(): Int {
        //TODO
        return 0
    }

    override fun getAdditionalDefinitionSearchFlags(): Int {
        //TODO
        return 0
    }

    override fun getAdditionalReferenceSearchFlags(): Int {
        //TODO
        return 0
    }

    override fun getNamedElement(element: PsiElement): PsiElement? {
        return findClass(element)
    }

    private fun findClass(element: PsiElement?): PsiElement? {
        if (element == null) {
            return null
        } else if (element is NabuPsiClass) {
            return element
        } else {
            return findClass(element.parent)
        }
    }
}