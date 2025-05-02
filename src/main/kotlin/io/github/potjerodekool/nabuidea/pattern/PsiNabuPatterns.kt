package io.github.potjerodekool.nabuidea.pattern

import com.intellij.patterns.StandardPatterns

object PsiNabuPatterns : StandardPatterns() {

    fun psiClass(): PsiClassPattern {
        return PsiClassPattern()
    }
}