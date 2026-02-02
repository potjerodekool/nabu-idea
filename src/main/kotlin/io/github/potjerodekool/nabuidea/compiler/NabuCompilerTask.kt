package io.github.potjerodekool.nabuidea.compiler

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.compiler.CompileContext
import com.intellij.openapi.compiler.CompileTask
import com.intellij.openapi.compiler.CompilerMessageCategory
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.modules
import com.intellij.openapi.roots.CompilerModuleExtension
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.roots.OrderEnumerator
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.util.Computable
import com.intellij.openapi.vfs.VirtualFile
import io.github.potjerodekool.nabu.compiler.client.CompilerOptionBuilder
import io.github.potjerodekool.nabu.compiler.client.DaemonEvent
import io.github.potjerodekool.nabu.compiler.client.DiagnosticEvent
import io.github.potjerodekool.nabu.compiler.client.LightweightClient
import io.github.potjerodekool.nabuidea.NabuFileType
import java.io.File
import java.util.logging.Level
import java.util.logging.Logger


class NabuCompilerTask : CompileTask {

    private val logger = Logger.getLogger(NabuCompilerTask::class.java.name)

    private val daemonLauncher = DaemonLauncher()

    override fun execute(context: CompileContext): Boolean {
        ensureDaemonRunning(context)

        val optionsBuilder = CompilerOptionBuilder()
        val project = context.project

        configureClassPath(context, project, optionsBuilder)
        configureSourceRoots(context, project, optionsBuilder)

        val compilerOptions = optionsBuilder.build()

        val client = LightweightClient()
        client.compile(
            compilerOptions
            , { event ->
                handleEvent(event, context)
            }
        )

        return true
    }

    private fun handleEvent(event: DaemonEvent,
                            context: CompileContext) {
        when(event) {
            is DiagnosticEvent -> {
                val category = when(event.kind()) {
                    DiagnosticEvent.Kind.ERROR -> CompilerMessageCategory.ERROR
                    DiagnosticEvent.Kind.WARN, DiagnosticEvent.Kind.MANDATORY_WARNING -> CompilerMessageCategory.WARNING
                    DiagnosticEvent.Kind.NOTE, DiagnosticEvent.Kind.OTHER -> CompilerMessageCategory.INFORMATION
                }

                context.addMessage(
                    category,
                    event.message(),
                    event.fileName(),
                    -1,
                    1
                )
            }
        }
    }

    /**
     * Ensure daemon is running, start if needed
     */
    private fun ensureDaemonRunning(context: CompileContext) {
        if (!daemonLauncher.isRunning) {
            logger.log(Level.INFO, "Starting Nabu compiler daemon...")
            context.addMessage(
                CompilerMessageCategory.INFORMATION,
                "Starting Nabu compiler daemon...",
                null, -1, -1
            )

            daemonLauncher.start()

            if (!daemonLauncher.waitUntilReady(10)) {
                throw RuntimeException("Failed to start Nabu compiler daemon")
            }

            logger.info("Nabu Daemon started successfully")
            context.addMessage(
                CompilerMessageCategory.INFORMATION,
                "Nabu compiler daemon ready",
                null, -1, -1
            )
        }
    }

    private fun configureClassPath(context: CompileContext,
                                   project: Project,
                                   compilerOptionBuilder: CompilerOptionBuilder) {
        val paths = ArrayList<String>()
        val moduleManager = ModuleManager.getInstance(project)

        /*
        val targetSdk =
            if (module != null) ModuleRootManager.getInstance(module).getSdk() else ProjectRootManager.getInstance(
                project
            ).getProjectSdk()
        */

        val sdk = ProjectRootManager.getInstance(project).getProjectSdk()

        val orderEnumerator = ProjectRootManager.getInstance(project).orderEntries()

        orderEnumerator.compileOnly().sdkOnly().pathsList.pathList.forEach { path ->
            paths.add(path)
        }

        project.modules.forEach { module ->
            OrderEnumerator.orderEntries(module).compileOnly().withoutSdk().pathsList.pathList.forEach { path ->
                paths.add(path)
            }
        }

        moduleManager.modules
            .mapNotNull { module -> getModuleOutputDirectory(module) }
            .forEach { outputDirectory ->
                paths.add(outputDirectory)
            }

        compilerOptionBuilder.classPath(paths)

        context.addMessage(
            CompilerMessageCategory.INFORMATION,
            "CLASS_PATH ${paths.joinToString(File.pathSeparator)}",
            null,
            0,
            0
        )

        moduleManager.modules.forEach { module ->

            val projectBasePath = project.basePath
            if (projectBasePath != null) {
                val outputDirectory = projectBasePath + File.separator + "out" + File.separator +
                        "production" + File.separator + module.name

                context.addMessage(
                    CompilerMessageCategory.INFORMATION,
                    "Outputdirectory $outputDirectory",
                    null,
                    0,
                    0
                )

                compilerOptionBuilder.output(
                    outputDirectory
                )
            }
        }
    }

    private fun configureSourceRoots(context: CompileContext,
                                     project: Project,
                                     compilerOptionsBuilder: CompilerOptionBuilder) {
        val application = ApplicationManager.getApplication()

        if (!context.isRebuild) {
            //TODO get affected files for incremental build

            val files = application.runReadAction(
                Computable<Array<VirtualFile>> {
                    context.compileScope.getFiles(NabuFileType, true)
                }
            )
        }

        val sourceRoots = getSourceRoots(project)
        val sourcePath = sourceRoots
            .mapNotNull { it.canonicalPath }
            .joinToString(File.pathSeparator)

        compilerOptionsBuilder.sourcePath(sourcePath)

        context.addMessage(
            CompilerMessageCategory.INFORMATION,
            "SourcePath $sourcePath",
            null,
            0,
            0
        )
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

    fun dispose() {
        if (daemonLauncher.isRunning) {
            logger.info("Stopping daemon on plugin disposal")
            daemonLauncher.stop()
        }
    }
}
