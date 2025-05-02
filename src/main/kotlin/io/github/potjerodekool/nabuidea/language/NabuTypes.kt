package io.github.potjerodekool.nabuidea.language

import io.github.potjerodekool.nabu.NabuLexer
import io.github.potjerodekool.nabu.NabuParser
import io.github.potjerodekool.nabuidea.NabuLanguage
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory
import org.antlr.intellij.adaptor.lexer.RuleIElementType
import org.antlr.intellij.adaptor.lexer.TokenIElementType

object NabuTypes {
    private val tokens = PSIElementTypeFactory.getTokenIElementTypes(NabuLanguage).associateBy { it.antlrTokenType }
    private val rules = PSIElementTypeFactory.getRuleIElementTypes(NabuLanguage).associateBy { it.ruleIndex }

    val PUBLIC = findTokenIElementType(NabuLexer.PUBLIC)
    val PROTECTED = findTokenIElementType(NabuLexer.PROTECTED)
    val PRIVATE = findTokenIElementType(NabuLexer.PRIVATE)
    val ABSTRACT = findTokenIElementType(NabuLexer.ABSTRACT)
    val STATIC = findTokenIElementType(NabuLexer.STATIC)
    val FINAL = findTokenIElementType(NabuLexer.FINAL)
    val PACKAGE = findTokenIElementType(NabuLexer.PACKAGE)
    val IMPORT = findTokenIElementType(NabuLexer.IMPORT)
    val EXTENDS = findTokenIElementType(NabuLexer.EXTENDS)
    val IMPLEMENTS = findTokenIElementType(NabuLexer.IMPLEMENTS)
    val SEALED = findTokenIElementType(NabuLexer.SEALED)
    val NON_SEALED = findTokenIElementType(NabuLexer.NONSEALED)
    val STRICTFP = findTokenIElementType(NabuLexer.STRICTFP)
    val FUN = findTokenIElementType(NabuLexer.FUN)
    val RETURN = findTokenIElementType(NabuLexer.RETURN)
    val THIS = findTokenIElementType(NabuLexer.THIS)
    val VOID = findTokenIElementType(NabuLexer.VOID)
    val BOOLEAN = findTokenIElementType(NabuLexer.BOOLEAN)
    val CHAR = findTokenIElementType(NabuLexer.CHAR)
    val BYTE = findTokenIElementType(NabuLexer.BYTE)
    val SHORT = findTokenIElementType(NabuLexer.SHORT)
    val INT = findTokenIElementType(NabuLexer.INT)
    val FLOAT = findTokenIElementType(NabuLexer.FLOAT)
    val LONG = findTokenIElementType(NabuLexer.LONG)
    val DOUBLE = findTokenIElementType(NabuLexer.DOUBLE)
    val VAR = findTokenIElementType(NabuLexer.VAR)
    val NULL = findTokenIElementType(NabuLexer.NullLiteral)
    val CLASS = findTokenIElementType(NabuLexer.CLASS)
    val INTERFACE = findTokenIElementType(NabuLexer.INTERFACE)
    val ENUM = findTokenIElementType(NabuLexer.ENUM)
    val RECORD = findTokenIElementType(NabuLexer.RECORD)
    val PERMITS = findTokenIElementType(NabuLexer.PERMITS)
    val FOR = findTokenIElementType(NabuLexer.FOR)
    val WHILE = findTokenIElementType(NabuLexer.WHILE)
    val DO = findTokenIElementType(NabuLexer.DO)
    val SWITCH = findTokenIElementType(NabuLexer.SWITCH)
    val CASE = findTokenIElementType(NabuLexer.CASE)
    val DEFAULT = findTokenIElementType(NabuLexer.DEFAULT)
    val TYPE_IDENTIFIER = findRuleIElementType(NabuParser.RULE_typeIdentifier)
    val IDENTIFIER = findTokenIElementType(NabuLexer.Identifier);
    val LOCAL_VARIABLE_TYPE = findRuleIElementType(NabuParser.RULE_localVariableType)

    val CONSTRUCTOR_DECLARATOR = findRuleIElementType(NabuParser.RULE_constructorDeclarator)
    val SIMPLE_TYPE_NAME = findRuleIElementType(NabuParser.RULE_simpleTypeName);
    val NORMAL_CLASS_DECLARATION = findRuleIElementType(NabuParser.RULE_normalClassDeclaration)
    val PACKAGE_DECLARATION = findRuleIElementType(NabuParser.RULE_packageDeclaration)
    val NEW = findTokenIElementType(NabuLexer.NEW)

    private fun findTokenIElementType(antlrTokenType: Int): TokenIElementType {
        return tokens[antlrTokenType]!!
    }

    private fun findRuleIElementType(ruleIndex: Int): RuleIElementType {
        return rules[ruleIndex]!!
    }

}

