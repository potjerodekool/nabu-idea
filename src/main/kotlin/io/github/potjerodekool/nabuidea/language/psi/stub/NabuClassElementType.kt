package io.github.potjerodekool.nabuidea.language.psi.stub

import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.potjerodekool.nabuidea.language.NabuStubIndexKeys
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration
import io.github.potjerodekool.nabuidea.language.psi.stub.impl.NabuClassStubImpl

class NabuClassElementType :
        NabuStubElementType<NabuClassStub, NabuClassDeclaration>(
        "CLASS"
    ) {
    override fun createPsi(stub: NabuClassStub): NabuClassDeclaration? {
        TODO()
    }

    override fun createStub(p0: NabuClassDeclaration, p1: StubElement<out PsiElement?>?): NabuClassStub {
        TODO()
    }

    override fun getExternalId(): String {
        return "Nabu.class"
    }

    override fun serialize(
        stub: NabuClassStub,
        dataStream: StubOutputStream
    ) {
        dataStream.writeName(stub.getName())
    }

    override fun deserialize(
        dataStream: StubInputStream,
        parentStub: StubElement<*>?
    ): NabuClassStub {
        val name = dataStream.readName()
        return NabuClassStubImpl(parentStub, this, name?.getString() ?: "")
    }

    override fun indexStub(
        stub: NabuClassStub,
        sink: IndexSink
    ) {
        sink.occurrence(NabuStubIndexKeys.CLAZZ, stub.getName());
    }
}