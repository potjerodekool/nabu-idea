package io.github.potjerodekool.nabuidea.language

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import io.github.potjerodekool.nabuidea.language.psi.NabuTypes

class NabuSyntaxHighlighter : SyntaxHighlighterBase() {

    companion object {
        val COMMENT: TextAttributesKey =
            createTextAttributesKey("SIMPLE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val BAD_CHARACTER: TextAttributesKey =
            createTextAttributesKey("SIMPLE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)

        private val PACKAGE : Array<TextAttributesKey> = createKeyWordTextAttributesKeyArray("PACKAGE")
        private val CLASS : Array<TextAttributesKey> = createKeyWordTextAttributesKeyArray("CLASS")
        private val PUBLIC : Array<TextAttributesKey> = createKeyWordTextAttributesKeyArray("PUBLIC")
        private val PROTECTED : Array<TextAttributesKey> = createKeyWordTextAttributesKeyArray("PROTECTED")
        private val PRIVATE : Array<TextAttributesKey> = createKeyWordTextAttributesKeyArray("PRIVATE")
        private val ABSTRACT : Array<TextAttributesKey> = createKeyWordTextAttributesKeyArray("ABSTRACT")

        private val STATIC : Array<TextAttributesKey> = createKeyWordTextAttributesKeyArray("STATIC")
        private val FINAL : Array<TextAttributesKey> = createKeyWordTextAttributesKeyArray("FINAL")
        private val SEALED : Array<TextAttributesKey> = createKeyWordTextAttributesKeyArray("SEALED")
        private val NON_SEALED : Array<TextAttributesKey> = createKeyWordTextAttributesKeyArray("NON_SEALED")
        private val STRICTFP : Array<TextAttributesKey> = createKeyWordTextAttributesKeyArray("STRICTFP")

        private val BAD_CHAR_KEYS: Array<TextAttributesKey> = arrayOf<TextAttributesKey>(BAD_CHARACTER)
        private val EMPTY_KEYS: Array<TextAttributesKey?> = arrayOfNulls<TextAttributesKey>(0)

        private fun createKeyWordTextAttributesKeyArray(externalName: String) : Array<TextAttributesKey> {
            return arrayOf(
                createTextAttributesKey("PACKAGE", DefaultLanguageHighlighterColors.KEYWORD)
            )
        }
    }

    override fun getHighlightingLexer(): Lexer {
        return NabuLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<out TextAttributesKey?> {
        if (tokenType == TokenType.BAD_CHARACTER) {
            return BAD_CHAR_KEYS
        }
        if (tokenType == NabuTypes.PACKAGE) {
            return PACKAGE
        }
        if (tokenType == NabuTypes.CLASS) {
            return CLASS
        }
        if (tokenType == NabuTypes.PUBLIC) {
            return PUBLIC
        }
        if (tokenType == NabuTypes.PROTECTED) {
            return PROTECTED
        }
        if (tokenType == NabuTypes.PRIVATE) {
            return PRIVATE
        }
        if (tokenType == NabuTypes.ABSTRACT) {
            return ABSTRACT
        }
        if (tokenType == NabuTypes.STATIC) {
            return STATIC
        }
        if (tokenType == NabuTypes.FINAL) {
            return FINAL
        }
        if (tokenType == NabuTypes.SEALED) {
            return SEALED
        }
        if (tokenType == NabuTypes.NON_SEALED) {
            return NON_SEALED
        }
        if (tokenType == NabuTypes.STRICTFP) {
            return STRICTFP
        }

        return EMPTY_KEYS
    }

}