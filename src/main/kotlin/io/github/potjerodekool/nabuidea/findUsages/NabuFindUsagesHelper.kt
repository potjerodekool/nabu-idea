package io.github.potjerodekool.nabuidea.findUsages

import com.intellij.openapi.application.ApplicationManager
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import io.github.potjerodekool.nabuidea.language.NabuPsiUtils

object NabuFindUsagesHelper {

    fun getElementNames(element: PsiElement): Set<String> {
        val result = HashSet<String>()

        ApplicationManager.getApplication().runReadAction {
            if (element is PsiClass) {
                val qualifiedName = element.qualifiedName

                if (qualifiedName != null) {
                    result.add(qualifiedName)

                    val topLevelClass = NabuPsiUtils.getTopLevelClass(element)

                    if (topLevelClass != null) {
                        val topName = topLevelClass.qualifiedName

                        if (topName != null
                            && qualifiedName.length > topName.length) {
                            result.add(topName + qualifiedName.substring(topName.length).replace('.', '$'))
                        }
                    }
                }
            }
        }

        return result
    }
}