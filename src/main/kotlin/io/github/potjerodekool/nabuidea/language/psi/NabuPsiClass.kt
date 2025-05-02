package io.github.potjerodekool.nabuidea.language.psi

import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiQualifiedNamedElement

interface NabuPsiClass : PsiNameIdentifierOwner, PsiMember, PsiQualifiedNamedElement {
}
