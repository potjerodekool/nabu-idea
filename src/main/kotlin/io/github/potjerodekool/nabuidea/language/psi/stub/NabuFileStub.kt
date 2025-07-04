package io.github.potjerodekool.nabuidea.language.psi.stub

import com.intellij.psi.stubs.PsiClassHolderFileStub
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuFileImpl

interface NabuFileStub : PsiClassHolderFileStub<NabuFileImpl> {

    fun getPsiFactory(): NabuStubPsiFactory
    fun getName(): String
}