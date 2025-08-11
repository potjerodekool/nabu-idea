package io.github.potjerodekool.nabuidea.language.psi

import com.intellij.openapi.util.NlsSafe
import com.intellij.psi.PsiClass
import com.intellij.util.IncorrectOperationException

interface PsiClassOwner {

    fun getClasses(): Array<PsiClass?>

    fun getPackageName(): @NlsSafe String?

    @Throws(IncorrectOperationException::class)
    fun setPackageName(var1: String)
}