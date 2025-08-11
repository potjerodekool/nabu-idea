package io.github.potjerodekool.nabuidea.findUsages

import com.intellij.find.findUsages.FindUsagesOptions
import com.intellij.find.findUsages.JavaFindUsagesHelper
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.search.PsiElementProcessor
import com.intellij.psi.search.PsiElementProcessorAdapter
import com.intellij.usageView.UsageInfo
import com.intellij.util.Processor
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration
import io.github.potjerodekool.nabuidea.runReadAction
import io.github.potjerodekool.nabuidea.search.HierarchySearchRequest
import java.util.*

class NabuFindClassUsagesHandler(element: NabuClassDeclaration,
                                 factory: NabuFindUsagesHandlerFactory,
                                 elementsToSearch: Collection<PsiElement> = emptyList()) : NabuFindUsagesHandler<NabuClassDeclaration>(element, factory, elementsToSearch) {

    override fun createSearcher(
        element: PsiElement,
        processor: Processor<in UsageInfo>,
        options: FindUsagesOptions
    ): Searcher {
        return MySearcher(element, processor, options)
    }

    private class MySearcher(
        element: PsiElement, processor: Processor<in UsageInfo>, options: FindUsagesOptions
    ) : Searcher(element, processor, options) {

        private val nabuOptions = options as NabuClassFindUsagesOptions
        private val referenceProcessor = createReferenceProcessor(processor)

        override fun buildTaskList(forHighlight: Boolean): Boolean {
            if (nabuOptions.isDerivedClasses || nabuOptions.isDerivedInterfaces) {
                processInheritorsLater()
            }

            return true
        }

        private fun processInheritorsLater() {
            val request = HierarchySearchRequest(element, options.searchScope, nabuOptions.isCheckDeepInheritance)
            addTask {
                request.searchInheritors().forEach(
                    PsiElementProcessorAdapter(
                        PsiElementProcessor { element ->
                            runReadAction {
                                if (!element.isValid) return@runReadAction false
                                val isInterface = element.isInterface
                                when {
                                    isInterface && nabuOptions.isDerivedInterfaces || !isInterface && nabuOptions.isDerivedClasses ->
                                        processUsage(processor, element.navigationElement)

                                    else -> true
                                }
                            }
                        }
                    )
                )
            }
        }

    }

    override fun getStringsToSearch(element: PsiElement): Collection<String> {
        val psiClass = when (element) {
            is NabuClassDeclaration -> element
            is PsiClass -> element
            else -> null
        } ?: return Collections.emptyList()

        return JavaFindUsagesHelper.getElementNames(psiClass)
    }

    override fun isSearchForTextOccurrencesAvailable(psiElement: PsiElement, isSingleFile: Boolean): Boolean {
        return !isSingleFile
    }

    override fun getFindUsagesOptions(dataContext: DataContext?): FindUsagesOptions {
        return factory.findClassOptions
    }
}