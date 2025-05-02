package io.github.potjerodekool.nabuidea.language.rename

import com.intellij.openapi.util.NlsContexts
import com.intellij.refactoring.rename.naming.AutomaticRenamer

//TODO get text from NabuRefactoringBundle
class NabuAutomaticRenamer : AutomaticRenamer() {

    override fun getDialogTitle(): @NlsContexts.DialogTitle String? {
        return "Rename"
    }

    override fun getDialogDescription(): @NlsContexts.Label String? {
        return "Rename"
    }

    override fun entityName(): @NlsContexts.ColumnName String? {
        return "Rename"
    }

}