package io.github.potjerodekool.nabuidea.language.psi.light

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiPackage
import com.intellij.psi.augment.PsiAugmentProvider
import io.github.potjerodekool.nabuidea.language.NabuUtils
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration

class NabuJavaAugmentProvider : PsiAugmentProvider() {

    fun findClasses(
        myElement: PsiElement,
        nameHint: String?,
        importList: List<String>
    ): List<PsiElement> {
        if (nameHint == null) {
            return emptyList()
        }

        val project = myElement.project

        return NabuUtils.findClassesByFilter(project) { element ->
            val clazz = element as NabuClassDeclaration
            val qName = clazz.qualifiedName
            /*
            if (!importList.contains(qName)) {
                return@findClassesByFilter false
            }
            */

            qName != null &&
                    (nameHint == qName
                            || qName.endsWith(nameHint))
        }
    }

    override fun <Psi : PsiElement?> getAugments(
        element: PsiElement,
        type: Class<Psi?>,
        nameHint: String?
    ): List<Psi?> {
        if (type == PsiClass::class.java) {
            //(((element.parent as PsiJavaFile).importList as PsiImportList).importStatements[0] as PsiImportStatement).qualifiedName
            val file = findFile(element)

            if (file == null) {
                return getArgumentsDefault(element, type, nameHint)
            }

            val singleImports = file.getSingleClassImports(false)
            val onDemandImports = file.getOnDemandImports(true, false)

            val classList = onDemandImports
                .map { it as PsiPackage }
                .flatMap { it.classes.toList() }

            val importList = file.importList

            if (importList == null) {
                return getArgumentsDefault(element, type, nameHint)
            }

            val importStatements = importList.importStatements

            val names = importStatements
                .map { it.qualifiedName }
                .filterNotNull()

            val classes = findClasses(element, nameHint, names)

            if (classes.isNotEmpty()) {
                val result = classes
                    .map { it as NabuClassDeclaration }
                    .map { NabuLightClass(it) }
                    .map { it as Psi }
                return result
            }
        } else if (type is PsiPackage) {
            TODO()
        }

        return getArgumentsDefault(element, type, nameHint)
    }

    private fun <Psi : PsiElement?> getArgumentsDefault(element: PsiElement,
                                                        type: Class<Psi?>,
                                                        nameHint: String?): List<Psi?> {
        return super.getAugments(element, type, nameHint)
    }


    private fun findFile(element: PsiElement?): PsiJavaFile? {
        return when (element) {
            null -> null
            is PsiJavaFile -> element
            else -> findFile(element.parent)
        }
    }

    override fun <Psi : PsiElement?> getAugments(
        element: PsiElement,
        type: Class<Psi?>
    ): List<Psi?> {
        return super.getAugments(element, type)
    }
}