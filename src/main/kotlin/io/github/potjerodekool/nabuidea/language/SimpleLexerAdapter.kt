package io.github.potjerodekool.nabuidea.language

import com.intellij.lexer.FlexAdapter

class SimpleLexerAdapter : FlexAdapter(NabuLexer(null)) {
}