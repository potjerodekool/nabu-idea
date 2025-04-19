package io.github.potjerodekool.nabuidea

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object NabuFileType : LanguageFileType(NabuLanguage) {

    override fun getName(): String {
        return "Nabu"
    }

    override fun getDescription(): String {
        return "Nabu language file"
    }

    override fun getDefaultExtension(): String {
        return "nabu"
    }

    override fun getIcon(): Icon? {
        return Icons.FILE
    }
}