package io.github.potjerodekool.nabuidea.language

import com.intellij.psi.tree.TokenSet

interface NabuTokenSets {
    companion object {
        val IDENTIFIERS = TokenSet.create(
            NabuTypes.IDENTIFIER,
            NabuTypes.TYPE_IDENTIFIER,
            NabuTypes.LOCAL_VARIABLE_TYPE
        )

        //val COMMENTS: TokenSet = TokenSet.create(NabuTypes.COMMENT)
        val COMMENTS: TokenSet = TokenSet.create()
    }
}