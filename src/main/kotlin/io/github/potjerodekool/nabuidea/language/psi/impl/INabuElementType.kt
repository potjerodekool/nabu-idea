package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.psi.tree.IElementType
import io.github.potjerodekool.nabuidea.NabuLanguage

class INabuElementType(debugName: String, private val leftBound: Boolean = false) :
    IElementType(debugName, NabuLanguage) {

    override fun isLeftBound(): Boolean {
        return leftBound
    }
}