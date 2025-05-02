package io.github.potjerodekool.nabuidea.pattern

import com.intellij.patterns.PsiElementPattern
import io.github.potjerodekool.nabuidea.language.psi.PsiMember

open class PsiMemberPattern<T : PsiMember, Self : PsiMemberPattern<T, Self>>
protected constructor(aClass: Class<T>) :
    PsiElementPattern<T, Self>(aClass)