package io.github.potjerodekool.nabuidea.language.reference

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult
import io.github.potjerodekool.nabuidea.Icons
import io.github.potjerodekool.nabuidea.language.JavaUtils
import io.github.potjerodekool.nabuidea.language.NabuUtils
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration

class NabuReference(
    element: PsiElement,
    textRange: TextRange,
    private val text: String
) : PsiPolyVariantReferenceBase<PsiElement>(element, textRange) {


    fun findCurrentClass(): NabuClassDeclaration? {
        var element = myElement

        while (element != null && element !is NabuClassDeclaration) {
            element = element.parent
        }

        return element
    }

    fun findClasses(): List<PsiElement> {
        val project = myElement.project
        val currentClass = findCurrentClass()

        if (currentClass != null && !text.contains(".")) {
            val unit = currentClass.getCompilationUnit()!!
            val imports = unit.findImports()

            val dotText = ".${text}"

            val resolvedClass = imports
                .filter {
                    val importText = it.getImportText()
                    importText != null && importText.endsWith(dotText)
            }.map {
                    val reference = it.reference

                    if (reference == null) {
                        return@map null
                    } else {
                        return@map reference.resolve()
                    }
            }.firstOrNull()

            if (resolvedClass != null) {
                return listOf(
                    resolvedClass.originalElement
                )
            }
        }

        val resultList = mutableListOf<PsiElement>()

        val nabuResults = NabuUtils.findClassesByFilter(project, { element ->
            val clazz = element as NabuClassDeclaration
            val qName = clazz.qualifiedName
            qName != null &&
                    (text == qName
                            || qName.endsWith(text))
        })

        resultList.addAll(nabuResults)

        try {
            val className = "foo.Main"

            val psiClass = JavaPsiFacade.getInstance(project).findClass(className, element.resolveScope)

            if (psiClass != null) {
                resultList.add(psiClass)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return resultList
    }

    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult?> {
        val classes = findClasses()

        val results = classes.map { clazz ->
            PsiElementResolveResult(
                clazz
            )
        }.toTypedArray()

        return results
    }

    /*
    override fun resolve(): PsiElement? {
        //val qualifiedName = myElement.name
        var classDeclaration = myElement as NabuClassDeclaration

        classDeclaration as NavigatablePsiElement

        val qualifiedName = classDeclaration.qualifiedName

        return classDeclaration
        return NabuClassIndex()
            .get(qualifiedName, myElement.getProject(), GlobalSearchScope.allScope(myElement.getProject()))
            .stream().findFirst().orElse(null)
    }

     */

    override fun getVariants(): Array<out Any?> {
        val classes = findClasses()

        //LookupElementBuilder.create()

        val variants = classes.map { e ->
            val clazz = e as NabuClassDeclaration

            LookupElementBuilder.create(clazz)
                .withPsiElement(clazz)
                .withTypeText(clazz.qualifiedName)
                .withIcon(Icons.FILE)
        }.toTypedArray()


        return variants
    }
}