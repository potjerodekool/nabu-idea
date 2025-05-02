package io.github.potjerodekool.nabuidea.language.highlight

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import io.github.potjerodekool.nabu.NabuLexer
import io.github.potjerodekool.nabuidea.NabuLanguage
import io.github.potjerodekool.nabuidea.language.NabuTypes
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.TokenIElementType

class NabuSyntaxHighlighter : SyntaxHighlighterBase() {

    companion object {
        val COMMENT: TextAttributesKey =
            createTextAttributesKey("SIMPLE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val BAD_CHARACTER =
            arrayOf<TextAttributesKey>(createTextAttributesKey("SIMPLE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER))

        private val highlighters = mutableMapOf<Int, Array<TextAttributesKey>>()
        private val EMPTY_KEYS: Array<TextAttributesKey?> = arrayOfNulls<TextAttributesKey>(0)

        private fun createKeyWordTextAttributesKeyArray(externalName: String): Array<TextAttributesKey> {
            return arrayOf(
                createTextAttributesKey(externalName, DefaultLanguageHighlighterColors.KEYWORD)
            )
        }
    }

    init {
        addHighLiter(NabuTypes.PACKAGE, createKeyWordTextAttributesKeyArray("PACKAGE"))
        addHighLiter(NabuTypes.IMPORT, createKeyWordTextAttributesKeyArray("IMPORT"))
        addHighLiter(NabuTypes.EXTENDS, createKeyWordTextAttributesKeyArray("EXTENDS"))
        addHighLiter(NabuTypes.IMPLEMENTS, createKeyWordTextAttributesKeyArray("IMPLEMENTS"))
        addHighLiter(NabuTypes.PUBLIC, createKeyWordTextAttributesKeyArray("PUBLIC"))
        addHighLiter(NabuTypes.PROTECTED, createKeyWordTextAttributesKeyArray("PROTECTED"))
        addHighLiter(NabuTypes.PRIVATE, createKeyWordTextAttributesKeyArray("PRIVATE"))
        addHighLiter(NabuTypes.ABSTRACT, createKeyWordTextAttributesKeyArray("ABSTRACT"))
        addHighLiter(NabuTypes.STATIC, createKeyWordTextAttributesKeyArray("STATIC"))
        addHighLiter(NabuTypes.FINAL, createKeyWordTextAttributesKeyArray("FINAL"))
        addHighLiter(NabuTypes.SEALED, createKeyWordTextAttributesKeyArray("SEALED"))
        addHighLiter(NabuTypes.NON_SEALED, createKeyWordTextAttributesKeyArray("NON_SEALED"))
        addHighLiter(NabuTypes.STRICTFP, createKeyWordTextAttributesKeyArray("STRICTFP"))
        addHighLiter(NabuTypes.FUN, createKeyWordTextAttributesKeyArray("FUN"))
        addHighLiter(NabuTypes.RETURN, createKeyWordTextAttributesKeyArray("RETURN"))
        addHighLiter(NabuTypes.THIS, createKeyWordTextAttributesKeyArray("THIS"))
        addHighLiter(NabuTypes.VOID, createKeyWordTextAttributesKeyArray("VOID"))
        addHighLiter(NabuTypes.BOOLEAN, createKeyWordTextAttributesKeyArray("BOOLEAN"))
        addHighLiter(NabuTypes.CHAR, createKeyWordTextAttributesKeyArray("CHAR"))
        addHighLiter(NabuTypes.BYTE, createKeyWordTextAttributesKeyArray("BYTE"))
        addHighLiter(NabuTypes.SHORT, createKeyWordTextAttributesKeyArray("SHORT"))
        addHighLiter(NabuTypes.INT, createKeyWordTextAttributesKeyArray("INT"))
        addHighLiter(NabuTypes.FLOAT, createKeyWordTextAttributesKeyArray("FLOAT"))
        addHighLiter(NabuTypes.LONG, createKeyWordTextAttributesKeyArray("LONG"))
        addHighLiter(NabuTypes.DOUBLE, createKeyWordTextAttributesKeyArray("DOUBLE"))
        addHighLiter(NabuTypes.VAR, createKeyWordTextAttributesKeyArray("VAR"))
        addHighLiter(NabuTypes.NULL, createKeyWordTextAttributesKeyArray("NULL"))
        addHighLiter(NabuTypes.CLASS, createKeyWordTextAttributesKeyArray("CLASS"))
        addHighLiter(NabuTypes.INTERFACE, createKeyWordTextAttributesKeyArray("INTERFACE"))
        addHighLiter(NabuTypes.ENUM, createKeyWordTextAttributesKeyArray("ENUM"))
        addHighLiter(NabuTypes.RECORD, createKeyWordTextAttributesKeyArray("RECORD"))
        addHighLiter(NabuTypes.PERMITS, createKeyWordTextAttributesKeyArray("PERMITS"))
        addHighLiter(NabuTypes.FOR, createKeyWordTextAttributesKeyArray("FOR"))
        addHighLiter(NabuTypes.WHILE, createKeyWordTextAttributesKeyArray("WHILE"))
        addHighLiter(NabuTypes.DO, createKeyWordTextAttributesKeyArray("DO"))
        addHighLiter(NabuTypes.SWITCH, createKeyWordTextAttributesKeyArray("SWITCH"))
        addHighLiter(NabuTypes.CASE, createKeyWordTextAttributesKeyArray("CASE"))
        addHighLiter(NabuTypes.DEFAULT, createKeyWordTextAttributesKeyArray("DEFAULT"))
        addHighLiter(NabuTypes.NEW, createKeyWordTextAttributesKeyArray("NEW"))
    }

    private fun addHighLiter(tokenType: TokenIElementType,
                             value: Array<TextAttributesKey>) {
        highlighters.put(tokenType.antlrTokenType, value)
    }

    override fun getHighlightingLexer(): Lexer {
        return ANTLRLexerAdaptor(NabuLanguage, NabuLexer(null))
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<out TextAttributesKey?> {
        if (tokenType is TokenIElementType) {
            return highlighters.getOrDefault(tokenType.antlrTokenType, EMPTY_KEYS)
        } else if (tokenType == TokenType.BAD_CHARACTER) {
            return BAD_CHARACTER
        } else {
            return EMPTY_KEYS
        }
    }

}
