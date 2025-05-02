package io.github.potjerodekool.nabuidea

import com.intellij.lang.Language
import io.github.potjerodekool.nabu.NabuParser
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory

object NabuLanguage : Language("Nabu") {

    init {
        PSIElementTypeFactory.defineLanguageIElementTypes(
            NabuLanguage,
            NabuParser.tokenNames,
            NabuParser.ruleNames
        )
    }

}