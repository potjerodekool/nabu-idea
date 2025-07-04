package io.github.potjerodekool.nabuidea.language.psi.stub

import com.intellij.psi.PsiMember
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.stubs.NamedStub

interface NabuMemberStub<T> : NamedStub<T?> where T : PsiMember?, T : PsiNamedElement? {
}