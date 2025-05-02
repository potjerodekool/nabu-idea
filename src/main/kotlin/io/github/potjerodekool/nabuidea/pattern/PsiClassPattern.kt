package io.github.potjerodekool.nabuidea.pattern

import io.github.potjerodekool.nabuidea.language.psi.NabuPsiClass

class PsiClassPattern : PsiMemberPattern<NabuPsiClass, PsiClassPattern>(NabuPsiClass::class.java) {
}