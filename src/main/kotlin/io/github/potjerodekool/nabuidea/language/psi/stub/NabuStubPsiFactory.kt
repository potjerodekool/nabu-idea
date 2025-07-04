package io.github.potjerodekool.nabuidea.language.psi.stub

import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuFileImpl

abstract class NabuStubPsiFactory {
    abstract fun createClass(stub: NabuClassStub): NabuClassDeclaration?
    abstract fun createFile(stub: NabuFileStub): NabuFileImpl?
}