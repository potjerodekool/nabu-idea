package io.github.potjerodekool.nabuidea.language.rename

import com.intellij.openapi.util.NlsContexts
import com.intellij.psi.PsiElement
import com.intellij.refactoring.rename.naming.AutomaticRenamer
import com.intellij.refactoring.rename.naming.AutomaticRenamerFactory
import com.intellij.usageView.UsageInfo

class NabuAutomaticRenamerFactory : AutomaticRenamerFactory {

    override fun isApplicable(element: PsiElement): Boolean {
        return true
    }

    override fun getOptionName(): @NlsContexts.Checkbox String? {
        return "Hernoemen"
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun setEnabled(enabled: Boolean) {
    }

    override fun createRenamer(
        element: PsiElement?,
        newName: String?,
        usages: Collection<UsageInfo?>?
    ): AutomaticRenamer {
        return NabuAutomaticRenamer()
    }
}