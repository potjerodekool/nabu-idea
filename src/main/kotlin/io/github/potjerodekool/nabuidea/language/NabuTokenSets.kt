package io.github.potjerodekool.nabuidea.language

import com.intellij.psi.tree.TokenSet
import io.github.potjerodekool.nabuidea.language.psi.NabuTypes

interface NabuTokenSets {
    companion object {
        val IDENTIFIERS: TokenSet = TokenSet.create(NabuTypes.KEY)

        val COMMENTS: TokenSet = TokenSet.create(NabuTypes.COMMENT)
    }
}