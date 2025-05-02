package io.github.potjerodekool.nabuidea.language

import com.intellij.lang.Language
import com.intellij.navigation.ChooseByNameContributorEx
import com.intellij.navigation.GotoClassContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.PossiblyDumbAware
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.Processor
import com.intellij.util.containers.ContainerUtil
import com.intellij.util.indexing.FindSymbolParameters
import com.intellij.util.indexing.IdFilter
import io.github.potjerodekool.nabuidea.NabuLanguage
import io.github.potjerodekool.nabuidea.language.psi.NabuPsiClass

class NabuGotoClassContributor : ChooseByNameContributorEx,
    GotoClassContributor,
    PossiblyDumbAware {

    override fun processNames(
        processor: Processor<in String>,
        scope: GlobalSearchScope,
        filter: IdFilter?) {
        val project = scope.project!!

        val keys = ContainerUtil.map(
            NabuUtils.findClasses(project),
            {
                it.name
            }
        )

        ContainerUtil.process(
            keys,
            processor
        )
    }

    override fun processElementsWithName(
        name: String,
        processor: Processor<in NavigationItem>,
        parameters: FindSymbolParameters) {

        val items: List<NavigationItem> = ContainerUtil.map(
            NabuUtils.findClasses(
                parameters.project,
                name,
            ),
            {
                it -> it as NavigationItem
            }
        )

        ContainerUtil.process(
            items,
            processor
        )
    }

    override fun getQualifiedName(item: NavigationItem): String? {
        if (item is NabuPsiClass) {
            return getQualifiedNameForClass(item)
        } else {
            return null
        }
    }

    private fun getQualifiedNameForClass(psiClass: NabuPsiClass): String? {
        return psiClass.getQualifiedName()
    }

    override fun getQualifiedNameSeparator(): String? {
        return "$"
    }

    override fun getElementLanguage(): Language? {
        return NabuLanguage
    }

    override fun isDumbAware(): Boolean {
        return true
    }

}