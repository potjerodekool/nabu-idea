package io.github.potjerodekool.nabuidea.action

import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.InputValidatorEx
import com.intellij.openapi.util.NlsContexts
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import io.github.potjerodekool.nabuidea.Icons
import io.github.potjerodekool.nabuidea.NabuBundle
import org.jetbrains.annotations.NonNls

class NewFileAction : CreateFileFromTemplateAction(
    NabuBundle.message("action.new.file.text"),
    NabuBundle.message("action.new.file.description"),
    Icons.FILE
), DumbAware {

    override fun postProcess(createdElement: PsiFile, templateName: String?, customProperties: Map<String?, String?>?) {
        super.postProcess(createdElement, templateName, customProperties)
    }

    override fun buildDialog(
        project: Project,
        directory: PsiDirectory,
        builder: CreateFileFromTemplateDialog.Builder
    ) {
        builder.setTitle(NabuBundle.message("action.new.class.file.title"))
            .addKind(
                NabuBundle.message("action.new.file.dialog.class.title"),
                Icons.FILE,
                "Nabu Class"
            )

        builder.setValidator(NameValidator)
    }

    override fun getActionName(
        p0: PsiDirectory?,
        p1: @NonNls String,
        p2: @NonNls String?
    ): @NlsContexts.Command String {
        return NabuBundle.message("action.new.file.actionName")
    }

}

private object NameValidator : InputValidatorEx {

    private val FQNAME_SEPARATORS = charArrayOf('/', '\\', '.')

    override fun getErrorText(inputString: String): String? {
        if (inputString.trim().isEmpty()) {
            return NabuBundle.message("action.new.file.error.empty.name")
        }

        val parts: List<String> = inputString.split(*FQNAME_SEPARATORS)
        if (parts.any { it.trim().isEmpty() }) {
            return NabuBundle.message("action.new.file.error.empty.name.part")
        }

        return null
    }

    override fun checkInput(inputString: String): Boolean = true

    override fun canClose(inputString: String): Boolean = getErrorText(inputString) == null
}