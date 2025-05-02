package io.github.potjerodekool.nabuidea.language.psi.util

import com.intellij.psi.PsiElement
import io.github.potjerodekool.nabuidea.language.psi.NabuClassDeclaration

enum class NabuElementKind(val type: String) {

    CLASS("class"),
    ENUM("enum"),
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