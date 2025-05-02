package io.github.potjerodekool.nabuidea.language.psi.util

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import io.github.potjerodekool.nabuidea.NabuLanguage
import io.github.potjerodekool.nabuidea.language.psi.NabuFile
import io.github.potjerodekool.nabuidea.language.psi.NabuTypeIdentifier

object NabuElementFactory {

    fun createFile(project: Project, text: String): NabuFile {
        val name = "dummy.nabu"
        return PsiFileFactory.getInstance(project).createFileFromText(
            name,
            NabuLanguage,
            text,

        ) as NabuFile
    }

    fun createTypeIdentifier(project: Project,
                             identifier: String): NabuTypeIdentifier {
        val code = """
            class $identifier {
            }
        """.trimIndent()

        val file = createFile(project, code)

        return file.firstChild.firstChild.firstChild.firstChild.firstChild.firstChild.firstChild.nextSibling.nextSibling
            as NabuTypeIdentifier
    }
}