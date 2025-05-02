package io.github.potjerodekool.nabuidea.language

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import junit.framework.TestCase
import org.junit.Test


class NabuCodeInsightTest : LightPlatformCodeInsightFixture4TestCase() {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    @Test
    fun testFindUsages() {
        val usageInfos = myFixture.testFindUsages("FindUsagesTestData.nabu", "FindUsagesTestData.java")
        TestCase.assertEquals(1, usageInfos.size)
    }
}