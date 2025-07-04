package io.github.potjerodekool.nabuidea.language.structure

import com.intellij.psi.NavigatablePsiElement
//import io.github.potjerodekool.nabuidea.language.psi.NabuEnumDeclaration
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuFileImpl
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration

//import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration
//import io.github.potjerodekool.nabuidea.language.psi.NabuRecordDeclaration

object NabuStructureViewElementFactory {

    fun <E : NavigatablePsiElement> create(element: E): NabuStructureViewElement<*> {
        val result = when (element) {
            is NabuFileImpl -> NabuFileStructureViewElement(element)
            //is NabuClassDeclaration,
            //is NabuEnumDeclaration,
            //is NabuRecordDeclaration
              //  -> NabuTopLevelStructureViewElement(element)
            //TODO add others
            is NabuClassDeclaration
                -> NabuTopLevelStructureViewElement(element)
            else -> DefaultStructureViewElement(element)
        }

        return result
    }
}