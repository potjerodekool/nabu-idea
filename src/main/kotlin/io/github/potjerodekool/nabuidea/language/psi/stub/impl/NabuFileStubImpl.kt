package io.github.potjerodekool.nabuidea.language.psi.stub.impl

import com.intellij.psi.PsiClass
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubBase
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.tree.StubFileElementType
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuFileImpl
import io.github.potjerodekool.nabuidea.language.psi.stub.NabuFileStub
import io.github.potjerodekool.nabuidea.language.psi.stub.NabuStubPsiFactory

open class NabuFileStubImpl (
    parent: StubElement<*>?,
    elementType: IStubElementType<*,*>,
    private val name: String) : StubBase<NabuFileImpl>(parent, elementType), NabuFileStub {

    override fun getPsiFactory(): NabuStubPsiFactory {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        return this.name;
    }

    override fun getClasses(): Array<out PsiClass?> {
        TODO("Not yet implemented")
    }

    override fun getType(): StubFileElementType<*> {
        TODO("Not yet implemented")
    }

    override fun getInvalidationReason(): String? {
        TODO("Not yet implemented")
    }


}