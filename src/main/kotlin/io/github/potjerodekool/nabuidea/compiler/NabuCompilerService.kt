package io.github.potjerodekool.nabuidea.compiler

import com.intellij.openapi.diagnostic.Logger
import java.io.*


/**
 * Helper service die je eigen Nabu compilation logica wraps
 * Vervang dit met je actuele Nabu compiler implementatie
 */
object NabuCompilerService {
    private val LOG = Logger.getInstance(NabuCompilerService::class.java)

    /**
     * Compileer een Nabu bestand naar Java class files
     *
     * @param nabuSourceFile Het Nabu source bestand
     * @param outputDirectory Directory waar class files gegenereerd worden
     * @return Liste met gegenereerde class files
     */
    @Throws(IOException::class, InterruptedException::class)
    fun compileNabuFile(nabuSourceFile: File, outputDirectory: File): MutableList<File?> {
        val generatedClasses: MutableList<File?> = ArrayList<File?>()

        // OPTIE 1: Via externe executable
        if (hasExternalCompiler()) {
            generatedClasses.addAll(compileViaExternalCompiler(nabuSourceFile, outputDirectory))
        } else {
            generatedClasses.addAll(compileViaInternalCompiler(nabuSourceFile, outputDirectory))
        }

        return generatedClasses
    }

    /**
     * Compileer via externe Nabu compiler executable
     */
    @Throws(IOException::class, InterruptedException::class)
    private fun compileViaExternalCompiler(nabuSourceFile: File, outputDirectory: File): MutableList<File?> {
        val result: MutableList<File?> = ArrayList<File?>()

        val pb = ProcessBuilder(
            "nabu-compiler",  // Of het pad naar je compiler
            "--output", outputDirectory.getAbsolutePath(),
            "--source-file", nabuSourceFile.getAbsolutePath()
        )

        pb.redirectErrorStream(true)
        val process = pb.start()

        BufferedReader(
            InputStreamReader(process.getInputStream())
        ).use { reader ->
            var line: String?
            while ((reader.readLine().also { line = it }) != null) {
                LOG.info("Nabu compiler: " + line)
            }
        }
        val exitCode = process.waitFor()
        if (exitCode != 0) {
            throw RuntimeException("Nabu compiler fout met exit code: " + exitCode)
        }

        // Verzamel gegenereerde .class bestanden
        val classFiles = outputDirectory.listFiles { dir: File?, name: String? ->
            name!!.endsWith(".class") && isNewlyGenerated(
                File(dir, name)
            )
        }

        if (classFiles != null) {
            for (classFile in classFiles) {
                result.add(classFile)
                LOG.info("Gegenereerd: " + classFile.getAbsolutePath())
            }
        }

        return result
    }

    /**
     * Compileer via interne compiler API
     * Vervang dit met je actuele Nabu compiler implementatie
     */
    @Throws(IOException::class)
    private fun compileViaInternalCompiler(nabuSourceFile: File, outputDirectory: File?): MutableList<File?> {
        val result: MutableList<File?> = ArrayList<File?>()

        try {
            // TODO: Hier je eigen Nabu compiler aanroepen
            // Voorbeeld:
            /*
            NabuParser parser = new NabuParser();
            NabuCompilationUnit unit = parser.parse(nabuSourceFile);

            for (NabuClass nabuClass : unit.getClasses()) {
                byte[] classBytes = new JavaClassGenerator(nabuClass).generate();

                String classFileName = nabuClass.getQualifiedName()
                    .replace(".", File.separator) + ".class";
                File classFile = new File(outputDirectory, classFileName);

                classFile.getParentFile().mkdirs();
                Files.write(classFile.toPath(), classBytes);

                result.add(classFile);
            }
            */

            LOG.info("Nabu bestand gecompileerd: " + nabuSourceFile.getName())
        } catch (e: Exception) {
            throw IOException("Nabu compilation fout: " + e.message, e)
        }

        return result
    }

    /**
     * Check of externe compiler beschikbaar is
     */
    private fun hasExternalCompiler(): Boolean {
        try {
            val pb = ProcessBuilder("nabu-compiler", "--version")
            val process = pb.start()
            val exitCode = process.waitFor()
            return exitCode == 0
        } catch (e: Exception) {
            return false
        }
    }

    /**
     * Check of bestand recent gegenereerd is (ter voorkoming van duplicates)
     */
    private fun isNewlyGenerated(file: File): Boolean {
        val fileModified = file.lastModified()
        val currentTime = System.currentTimeMillis()
        // Assume het is nieuw als het minder dan 5 seconden geleden gegenereerd is
        return (currentTime - fileModified) < 5000
    }
}