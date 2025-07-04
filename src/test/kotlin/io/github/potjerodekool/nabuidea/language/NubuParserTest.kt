package io.github.potjerodekool.nabuidea.language

import com.intellij.testFramework.ParsingTestCase
import kotlin.test.Ignore

//https://github.com/antlr/antlr4-intellij-adaptor/blob/master/src/main/java/org/antlr/intellij/adaptor/lexer/PSITokenSource.java
@Ignore
class NubuParserTest : ParsingTestCase("", "nabu", NabuParserDefinition()) {

    fun testParsingTestData() {
        doTest(true)
    }

    /**
     * @return path to test data file directory relative to root of this module.
     */
    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    override fun includeRanges(): Boolean {
        return true
    }

}