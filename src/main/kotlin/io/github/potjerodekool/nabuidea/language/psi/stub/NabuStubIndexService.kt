package io.github.potjerodekool.nabuidea.language.psi.stub

import com.intellij.openapi.application.ApplicationManager
import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream

open class NabuStubIndexService protected constructor() {

    open fun serializeFileStub(stub: NabuFileStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.getName())
    }

    open fun deserializeFileStub(dataStream: StubInputStream): NabuFileStub {
        TODO()
        /*
        val name = dataStream.readName()!!.string
        return NabuFileStubImpl(
            null,
            NabuFileElementType(),
            name
        )
        */
    }

    open fun indexFile(stub: NabuFileStub, sink: IndexSink) {
        TODO("Not yet implemented")
    }

    companion object {
        @JvmStatic
        fun getInstance(): NabuStubIndexService {
            return ApplicationManager.getApplication().getService(NabuStubIndexService::class.java) ?: NO_INDEX
        }

        private val NO_INDEX = NabuStubIndexService()
    }
}