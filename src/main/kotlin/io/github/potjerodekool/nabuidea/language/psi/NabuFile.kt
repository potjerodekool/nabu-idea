package io.github.potjerodekool.nabuidea.language.psi

import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.StubBasedPsiElement
import io.github.potjerodekool.nabuidea.language.psi.stub.NabuFileStub

interface NabuFile : PsiNamedElement, StubBasedPsiElement<NabuFileStub>, NavigatablePsiElement {
}