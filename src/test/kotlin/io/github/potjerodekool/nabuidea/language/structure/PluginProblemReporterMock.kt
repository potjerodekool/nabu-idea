package io.github.potjerodekool.nabuidea.language.structure

import com.intellij.diagnostic.PluginException
import com.intellij.diagnostic.PluginProblemReporter

class PluginProblemReporterMock : PluginProblemReporter {
    override fun createPluginExceptionByClass(
        p0: String,
        p1: Throwable?,
        p2: Class<*>
    ): PluginException {
        TODO("Not yet implemented")
    }

}
