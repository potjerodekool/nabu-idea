package io.github.potjerodekool.nabuidea.language.psi.stub

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import com.intellij.psi.tree.ILightStubFileElementType
import io.github.potjerodekool.nabuidea.NabuLanguage

class NabuFileElementType
    : ILightStubFileElementType<NabuFileStub>(
    "nabu.FILE",
    NabuLanguage
    ) {


    override fun getExternalId(): String {
        return "nabu.FILE";
    }

    override fun serialize(
        stub: NabuFileStub,
        dataStream: StubOutputStream
    ) {
        NabuStubIndexService.getInstance().serializeFileStub(stub, dataStream)
    }

    override fun deserialize(
        dataStream: StubInputStream,
        parentStub: StubElement<*>?
    ): NabuFileStub {
        return NabuStubIndexService.getInstance().deserializeFileStub(dataStream)
    }

}