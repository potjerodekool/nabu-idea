package io.github.potjerodekool.nabuidea.language.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import io.github.potjerodekool.nabuidea.NabuFileType

object NabuElementFactory {
    fun createProperty(project: Project, name: String): NabuProperty {
        val file = createFile(project, name)
        return file.getFirstChild() as NabuProperty
    }

    fun createFile(project: Project, text: String): NabuFile {
        val name = "dummy.Nabu"
        return PsiFileFactory.getInstance(project).createFileFromText(name, NabuFileType, text) as NabuFile
    }

    fun createProperty(project: Project, name: String?, value: String?): NabuProperty? {
        val file = createFile(project, name + " = " + value)
        return file.getFirstChild() as NabuProperty?
    }

    fun createCRLF(project: Project): PsiElement? {
        val file = createFile(project, "\n")
        return file.getFirstChild()
    }
}
