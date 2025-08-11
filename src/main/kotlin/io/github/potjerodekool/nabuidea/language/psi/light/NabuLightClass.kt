package io.github.potjerodekool.nabuidea.language.psi.light

import com.intellij.psi.*
import com.intellij.psi.impl.light.LightModifierList
import com.intellij.psi.impl.light.LightPsiClassBase
import io.github.potjerodekool.nabuidea.NabuLanguage
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration
import io.github.potjerodekool.nabuidea.language.psi.util.NabuElementKind

class NabuLightClass(private val clazz: NabuClassDeclaration) : LightPsiClassBase(
    clazz.manager,
    NabuLanguage,
    clazz.name
) {

    override fun getModifierList(): PsiModifierList {
        return LightModifierList(clazz)
    }

    override fun clone(): Any {
        TODO("Not yet implemented")
    }

    override fun getExtendsList(): PsiReferenceList? {
        return clazz.extendsList
    }

    override fun getImplementsList(): PsiReferenceList? {
        return clazz.implementsList
    }

    override fun getFields(): Array<out PsiField?> {
        return clazz.fields
    }

    override fun getMethods(): Array<out PsiMethod?> {
        return clazz.methods
    }

    override fun getInnerClasses(): Array<out PsiClass?> {
        return clazz.innerClasses
    }

    override fun getInitializers(): Array<out PsiClassInitializer?> {
        return clazz.initializers
    }

    override fun getScope(): PsiElement? {
        return clazz.scope
    }

    override fun getContainingClass(): PsiClass? {
        return clazz.containingClass
    }

    override fun getTypeParameterList(): PsiTypeParameterList? {
        return clazz.typeParameterList
    }

    override fun getContainingFile(): PsiFile? {
        return clazz.containingFile
    }

    override fun isInterface(): Boolean {
        return clazz.kind == NabuElementKind.INTERFACE
    }

    override fun isAnnotationType(): Boolean {
        return clazz.kind == NabuElementKind.ANNOTATION
    }

    override fun isEnum(): Boolean {
        return clazz.kind == NabuElementKind.ENUM
    }

    override fun getNavigationElement(): PsiElement {
        return clazz
    }

    override fun getParent(): PsiElement? {
        return clazz.parent
    }

    override fun getName(): String {
        return clazz.name
    }

    override fun getQualifiedName(): String? {
        return clazz.qualifiedName
    }
}
