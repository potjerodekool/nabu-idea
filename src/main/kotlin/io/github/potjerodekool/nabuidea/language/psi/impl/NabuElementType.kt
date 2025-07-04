package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.psi.tree.IElementType
import io.github.potjerodekool.nabuidea.NabuLanguage

abstract class NabuElementType(debugName: String) : IElementType(debugName, NabuLanguage) {
}