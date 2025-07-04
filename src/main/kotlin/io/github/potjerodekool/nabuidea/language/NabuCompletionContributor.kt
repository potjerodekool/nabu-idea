package io.github.potjerodekool.nabuidea.language

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiImportStatement
import com.intellij.psi.PsiJavaCodeReferenceElement
import com.intellij.psi.PsiQualifiedNamedElement
import com.intellij.util.ProcessingContext
import org.jetbrains.uast.UastFacade
import org.jetbrains.uast.getContainingUFile

class NabuCompletionContributor : CompletionContributor() {

    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(PsiJavaCodeReferenceElement::class.java)
                .withParent(PsiImportStatement::class.java),
            object : CompletionProvider<CompletionParameters?>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    val position = parameters.position
                    val packageName = resolvePackageName(position)
                    val text = extractText(position)

                    val project = parameters.editor.project!!
                    val classes = NabuUtils
                        .findClassesByFilter(project, { element ->
                            if (element is PsiQualifiedNamedElement) {
                                val qualifiedName = element.qualifiedName
                                val match: Boolean

                                if (text.contains('.')) {
                                    match = qualifiedName != null && qualifiedName.lowercase()
                                        .contains(text)
                                } else if (packageName != null && !packageName.isEmpty()) {
                                    val textWithPackageNamePrefixed = "$packageName.$text"

                                    match = qualifiedName != null && qualifiedName.lowercase()
                                        .contains(textWithPackageNamePrefixed)
                                } else {
                                    match = false
                                }

                                if (match) {
                                    return@findClassesByFilter true
                                }
                            }

                            val name = element.name
                            val match = name != null && name.lowercase().contains(text)
                            return@findClassesByFilter match
                        })


                    for (element in classes) {
                        val name = element.name!!
                        result.addElement(LookupElementBuilder.create(name))
                    }
                }
            }
        )
    }

    private fun extractText(position: PsiElement): String {
        val text = position.text

        return if (text.endsWith("IntellijIdeaRulezzz")) {
            text.substring(0, text.length - "IntellijIdeaRulezzz".length).lowercase()
        } else {
            text.lowercase()
        }
    }

    private fun resolvePackageName(position: PsiElement): String? {
        // (UastFacade.convertElement(position, null).uastParent.uastParent as UImportStatement).importReference
        val uElement = UastFacade.convertElement(position, null)
        return uElement?.getContainingUFile()?.packageName
    }

}
