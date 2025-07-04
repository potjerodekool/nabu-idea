package io.github.potjerodekool.nabuidea.language.psi.stub.impl

import com.intellij.psi.stubs.StubBase
import com.intellij.psi.stubs.StubElement
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration
import io.github.potjerodekool.nabuidea.language.psi.stub.NabuClassStub
import io.github.potjerodekool.nabuidea.language.psi.stub.NabuStubElementType
import io.github.potjerodekool.nabuidea.language.psi.util.NabuElementKind

class NabuClassStubImpl(
    parent: StubElement<*>?,
    elementType: NabuStubElementType<*, *>,
    private val name: String
) : StubBase<NabuClassDeclaration>(parent, elementType), NabuClassStub {
    override fun getKind(): NabuElementKind {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        TODO("Not yet implemented")
    }
}