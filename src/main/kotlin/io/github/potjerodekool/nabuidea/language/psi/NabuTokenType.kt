package io.github.potjerodekool.nabuidea.language.psi

import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.java.IKeywordElementType

interface NabuTokenType : TokenType {

    companion object {
        val LPARENTH: IElementType = INabuElementType("LPARENTH")
        val RPARENTH: IElementType = INabuElementType("RPARENTH")
        val LBRACE: IElementType = INabuElementType("LBRACE")
        val RBRACE: IElementType = INabuElementType("RBRACE")
        val LBRACKET: IElementType = INabuElementType("LBRACKET")
        val RBRACKET: IElementType = INabuElementType("RBRACKET")
        val GT: IElementType = INabuElementType("GT")
        val LT: IElementType = INabuElementType("LT")
        val COMMA: IElementType = INabuElementType("COMMA")
        val IDENTIFIER: IElementType = INabuElementType("IDENTIFIER")
        val AT: IElementType = INabuElementType("AT")
        val QUEST: IElementType = INabuElementType("QUEST")
        val EXTENDS_KEYWORD: IElementType = INabuElementType("EXTENDS_KEYWORD")
        val SUPER_KEYWORD: IElementType = INabuElementType("SUPER_KEYWORD")
    }

}