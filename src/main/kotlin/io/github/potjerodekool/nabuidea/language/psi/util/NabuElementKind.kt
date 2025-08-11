package io.github.potjerodekool.nabuidea.language.psi.util

import com.intellij.psi.PsiElement
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration

enum class NabuElementKind(val type: String) {

    CLASS("class"),
    INTERFACE("interface"),
    ENUM("enum"),
    ANNOTATION("annotation"),
    RECORD("record");

    companion object {
        fun fromElement(element: PsiElement): NabuElementKind? {
            if (element is NabuClassDeclaration) {
                return element.kind
            }

            return null
        }
    }
}