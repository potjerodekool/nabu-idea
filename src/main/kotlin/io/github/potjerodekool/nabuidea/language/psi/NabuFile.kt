package io.github.potjerodekool.nabuidea.language.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import io.github.potjerodekool.nabuidea.NabuFileType
import io.github.potjerodekool.nabuidea.NabuLanguage

class NabuFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, NabuLanguage) {
    override fun getFileType(): FileType {
        return NabuFileType
    }

    override fun toString(): String {
        return "Simple File"
    }
}

