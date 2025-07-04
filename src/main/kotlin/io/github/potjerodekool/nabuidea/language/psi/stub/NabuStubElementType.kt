package io.github.potjerodekool.nabuidea.language.psi.stub

import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.PsiFileStub
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.potjerodekool.nabuidea.NabuLanguage

abstract class NabuStubElementType<SE : StubElement<*>, E : PsiElement>(debugName: String) :
    IStubElementType<SE, E>(debugName, NabuLanguage) {


    protected fun getPsiFactory(stub: SE): NabuStubPsiFactory {
        return this.getFileStub(stub).getPsiFactory()
    }

    private fun getFileStub(stub: SE): NabuFileStub {
        var parent: StubElement<*> = stub;

        while (parent !is PsiFileStub) {
            parent = parent.getParentStub();
        }

        return parent as NabuFileStub
    }

    override fun createPsi(p0: SE): E? {
        TODO("Not yet implemented")
    }

    override fun createStub(p0: E, p1: StubElement<out PsiElement?>?): SE {
        TODO("Not yet implemented")
    }

    override fun serialize(p0: SE, p1: StubOutputStream) {
        TODO("Not yet implemented")
    }

    override fun deserialize(p0: StubInputStream, p1: StubElement<*>?): SE {
        TODO("Not yet implemented")
    }

    override fun indexStub(p0: SE, p1: IndexSink) {
        TODO("Not yet implemented")
    }

}