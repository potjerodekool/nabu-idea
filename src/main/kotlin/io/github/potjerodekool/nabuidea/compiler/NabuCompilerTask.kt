package io.github.potjerodekool.nabuidea.compiler

import com.intellij.openapi.compiler.CompileContext
import com.intellij.openapi.compiler.CompileTask
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.CompilerModuleExtension
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.vfs.VirtualFile
import io.github.potjerodekool.nabu.compiler.CompilerOption
import io.github.potjerodekool.nabu.compiler.CompilerOptions.CompilerOptionsBuilder
import io.github.potjerodekool.nabu.compiler.NabuCompiler
import java.io.File


class NabuCompilerTask : CompileTask {
    override fun execute(context: CompileContext): Boolean {
        val project = context.project

        val nabuCompiler = NabuCompiler()
        val compilerOptionsBuilder = CompilerOptionsBuilder()

        configureClassPath(project, compilerOptionsBuilder)
        configureSourceRoots(project, compilerOptionsBuilder)

        try {
            nabuCompiler.compile(compilerOptionsBuilder.build())
        } catch (e: Exception) {
            return false
        }

        return true
    }

    private fun configureClassPath(project: Project,
                                   compilerOptionsBuilder: CompilerOptionsBuilder) {
        val paths = ArrayList<String>()

        val moduleManager = ModuleManager.getInstance(project)

        moduleManager.modules
            .mapNotNull { module -> getModuleOutputDirectory(module) }
            .forEach { outputDirectory ->
                paths.add(outputDirectory)
            }

        compilerOptionsBuilder.option(
            CompilerOption.CLASS_PATH,
            paths.joinToString(File.pathSeparator)
        )

        moduleManager.modules.forEach { module ->

            val projectBasePath = project.basePath
            if (projectBasePath != null) {
                val outputDirectory = projectBasePath + File.separator + "out" + File.separator +
                        "production" + File.separator + module.name

                compilerOptionsBuilder.option(
                    CompilerOption.TARGET_DIRECTORY,
                    outputDirectory
                )
            }
        }

    }

    private fun configureSourceRoots(project: Project,
                                     compilerOptionsBuilder: CompilerOptionsBuilder) {
        val sourceRoots = getSourceRoots(project)
        val sourcePath = sourceRoots
            .mapNotNull { it.canonicalPath }
            .joinToString(File.pathSeparator)
        compilerOptionsBuilder.option(CompilerOption.SOURCE_PATH, sourcePath)
    }

    private fun getModuleOutputDirectory(module: Module): String? {
        val compilerExtension = CompilerModuleExtension.getInstance(module)

        if (compilerExtension != null) {
            val outputDir = compilerExtension.compilerOutputPath
            if (outputDir != null) {
                return outputDir.path
            }
        }
        return null
    }

    fun getSourceRoots(project: Project): List<VirtualFile> {
        val moduleManager = ModuleManager.getInstance(project)

        return moduleManager.modules
            .map { module -> ModuleRootManager.getInstance(module) }
            .map { rootManager -> rootManager.sourceRoots }
            .flatMap { sourceRoots -> sourceRoots.toList() }
    }
}