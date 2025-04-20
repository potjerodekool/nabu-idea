package io.github.potjerodekool.nabuidea.language

import com.intellij.lexer.FlexAdapter

class NabuLexerAdapter : FlexAdapter(NabuLexer(null)) {
}