package io.github.potjerodekool.nabuidea.language

import com.intellij.psi.stubs.StubElement
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuNamedElement

interface NabuNamedElementStub : StubElement<NabuNamedElement> {

    fun getName(): String?
}