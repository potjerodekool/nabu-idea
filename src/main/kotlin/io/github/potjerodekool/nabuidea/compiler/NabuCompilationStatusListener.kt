package io.github.potjerodekool.nabuidea.compiler

import com.intellij.openapi.compiler.CompilationStatusListener
import com.intellij.openapi.compiler.CompileContext

class NabuCompilationStatusListener : CompilationStatusListener {

    override fun compilationFinished(aborted: Boolean, errors: Int, warnings: Int, compileContext: CompileContext) {
        println("Compilation finished. Errors: $errors, Warnings: $warnings");
    }
}