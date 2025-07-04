package io.github.potjerodekool.nabuidea.language.psi.stub

import com.intellij.psi.stubs.StubElement
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration
import io.github.potjerodekool.nabuidea.language.psi.util.NabuElementKind

interface NabuClassStub : StubElement<NabuClassDeclaration> {

    fun getKind(): NabuElementKind
    fun getName(): String
}