package io.github.potjerodekool.nabuidea.language

import com.intellij.testFramework.ParsingTestCase

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