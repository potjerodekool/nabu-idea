package io.github.potjerodekool.nabuidea.language.psi.util

import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.NlsSafe
import com.intellij.openapi.util.Pair
import com.intellij.openapi.util.TextRange
import com.intellij.psi.HierarchicalMethodSignature
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiClassInitializer
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiField
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiIdentifier
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiModifierList
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceList
import com.intellij.psi.PsiSubstitutor
import com.intellij.psi.PsiTypeParameter
import com.intellij.psi.PsiTypeParameterList
import com.intellij.psi.ResolveState
import com.intellij.psi.javadoc.PsiDocComment
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.SearchScope
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.Unmodifiable
import javax.swing.Icon

object ToPsi {

    fun toPsiClass(classDeclaration: NabuClassDeclaration) : PsiClass {
        return PsiClassImpl()
    }
}

class PsiClassImpl : PsiClass {
    override fun getQualifiedName(): @NlsSafe String? {
        TODO("Not yet implemented")
    }

    override fun isInterface(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isAnnotationType(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEnum(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getExtendsList(): PsiReferenceList? {
        TODO("Not yet implemented")
    }

    override fun getImplementsList(): PsiReferenceList? {
        TODO("Not yet implemented")
    }

    override fun getExtendsListTypes(): Array<out PsiClassType?> {
        TODO("Not yet implemented")
    }

    override fun getImplementsListTypes(): Array<out PsiClassType?> {
        TODO("Not yet implemented")
    }

    override fun getSuperClass(): PsiClass? {
        TODO("Not yet implemented")
    }

    override fun getInterfaces(): Array<out PsiClass?> {
        TODO("Not yet implemented")
    }

    override fun getSupers(): Array<out PsiClass?> {
        TODO("Not yet implemented")
    }

    override fun getSuperTypes(): Array<out PsiClassType?> {
        TODO("Not yet implemented")
    }

    override fun getFields(): Array<out PsiField?> {
        TODO("Not yet implemented")
    }

    override fun getMethods(): Array<out PsiMethod?> {
        TODO("Not yet implemented")
    }

    override fun getConstructors(): Array<out PsiMethod?> {
        TODO("Not yet implemented")
    }

    override fun getInnerClasses(): Array<out PsiClass?> {
        TODO("Not yet implemented")
    }

    override fun getInitializers(): Array<out PsiClassInitializer?> {
        TODO("Not yet implemented")
    }

    override fun getAllFields(): Array<out PsiField?> {
        TODO("Not yet implemented")
    }

    override fun getAllMethods(): Array<out PsiMethod?> {
        TODO("Not yet implemented")
    }

    override fun getAllInnerClasses(): Array<out PsiClass?> {
        TODO("Not yet implemented")
    }

    override fun findFieldByName(
        p0: @NonNls String?,
        p1: Boolean
    ): PsiField? {
        TODO("Not yet implemented")
    }

    override fun findMethodBySignature(
        p0: PsiMethod,
        p1: Boolean
    ): PsiMethod? {
        TODO("Not yet implemented")
    }

    override fun findMethodsBySignature(
        p0: PsiMethod,
        p1: Boolean
    ): Array<out PsiMethod?> {
        TODO("Not yet implemented")
    }

    override fun findMethodsByName(
        p0: @NonNls String?,
        p1: Boolean
    ): Array<out PsiMethod?> {
        TODO("Not yet implemented")
    }

    override fun findMethodsAndTheirSubstitutorsByName(
        p0: @NonNls String,
        p1: Boolean
    ): @Unmodifiable List<Pair<PsiMethod?, PsiSubstitutor?>?> {
        TODO("Not yet implemented")
    }

    override fun getAllMethodsAndTheirSubstitutors(): @Unmodifiable List<Pair<PsiMethod?, PsiSubstitutor?>?> {
        TODO("Not yet implemented")
    }

    override fun findInnerClassByName(
        p0: @NonNls String?,
        p1: Boolean
    ): PsiClass? {
        TODO("Not yet implemented")
    }

    override fun getLBrace(): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun getRBrace(): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun getNameIdentifier(): PsiIdentifier? {
        TODO("Not yet implemented")
    }

    override fun getScope(): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun isInheritor(p0: PsiClass, p1: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun isInheritorDeep(p0: PsiClass, p1: PsiClass?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getContainingClass(): PsiClass? {
        TODO("Not yet implemented")
    }

    override fun getVisibleSignatures(): Collection<HierarchicalMethodSignature?> {
        TODO("Not yet implemented")
    }

    override fun setName(p0: @NonNls String): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun getName(): @NlsSafe String? {
        TODO("Not yet implemented")
    }

    override fun getProject(): Project {
        TODO("Not yet implemented")
    }

    override fun getLanguage(): Language {
        TODO("Not yet implemented")
    }

    override fun getManager(): PsiManager? {
        TODO("Not yet implemented")
    }

    override fun getChildren(): Array<out PsiElement> {
        TODO("Not yet implemented")
    }

    override fun getParent(): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun getFirstChild(): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun getLastChild(): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun getNextSibling(): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun getPrevSibling(): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun getContainingFile(): PsiFile? {
        TODO("Not yet implemented")
    }

    override fun getTextRange(): TextRange? {
        TODO("Not yet implemented")
    }

    override fun getStartOffsetInParent(): Int {
        TODO("Not yet implemented")
    }

    override fun getTextLength(): Int {
        TODO("Not yet implemented")
    }

    override fun findElementAt(offset: Int): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun findReferenceAt(offset: Int): PsiReference? {
        TODO("Not yet implemented")
    }

    override fun getTextOffset(): Int {
        TODO("Not yet implemented")
    }

    override fun getText(): @NlsSafe String? {
        TODO("Not yet implemented")
    }

    override fun textToCharArray(): CharArray {
        TODO("Not yet implemented")
    }

    override fun getOriginalElement(): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun textMatches(text: @NonNls CharSequence): Boolean {
        TODO("Not yet implemented")
    }

    override fun textMatches(element: PsiElement): Boolean {
        TODO("Not yet implemented")
    }

    override fun textContains(c: Char): Boolean {
        TODO("Not yet implemented")
    }

    override fun accept(visitor: PsiElementVisitor) {
        TODO("Not yet implemented")
    }

    override fun acceptChildren(visitor: PsiElementVisitor) {
        TODO("Not yet implemented")
    }

    override fun copy(): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun add(element: PsiElement): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun addBefore(
        element: PsiElement,
        anchor: PsiElement?
    ): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun addAfter(
        element: PsiElement,
        anchor: PsiElement?
    ): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun checkAdd(element: PsiElement) {
        TODO("Not yet implemented")
    }

    override fun addRange(
        first: PsiElement?,
        last: PsiElement?
    ): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun addRangeBefore(
        first: PsiElement,
        last: PsiElement,
        anchor: PsiElement?
    ): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun addRangeAfter(
        first: PsiElement?,
        last: PsiElement?,
        anchor: PsiElement?
    ): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun delete() {
        TODO("Not yet implemented")
    }

    override fun checkDelete() {
        TODO("Not yet implemented")
    }

    override fun deleteChildRange(first: PsiElement?, last: PsiElement?) {
        TODO("Not yet implemented")
    }

    override fun replace(newElement: PsiElement): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun isValid(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isWritable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getReference(): PsiReference? {
        TODO("Not yet implemented")
    }

    override fun getReferences(): Array<out PsiReference?> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> getCopyableUserData(key: Key<T?>): T? {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> putCopyableUserData(key: Key<T?>, value: T?) {
        TODO("Not yet implemented")
    }

    override fun processDeclarations(
        processor: PsiScopeProcessor,
        state: ResolveState,
        lastParent: PsiElement?,
        place: PsiElement
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun getContext(): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun isPhysical(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getResolveScope(): GlobalSearchScope {
        TODO("Not yet implemented")
    }

    override fun getUseScope(): SearchScope {
        TODO("Not yet implemented")
    }

    override fun getNode(): ASTNode? {
        TODO("Not yet implemented")
    }

    override fun isEquivalentTo(another: PsiElement?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getNavigationElement(): PsiElement {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> getUserData(key: Key<T?>): T? {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> putUserData(key: Key<T?>, value: T?) {
        TODO("Not yet implemented")
    }

    override fun getIcon(flags: Int): Icon? {
        TODO("Not yet implemented")
    }

    override fun getModifierList(): PsiModifierList? {
        TODO("Not yet implemented")
    }

    override fun hasModifierProperty(p0: @NonNls String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isDeprecated(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPresentation(): ItemPresentation? {
        TODO("Not yet implemented")
    }

    override fun getDocComment(): PsiDocComment? {
        TODO("Not yet implemented")
    }

    override fun hasTypeParameters(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getTypeParameterList(): PsiTypeParameterList? {
        TODO("Not yet implemented")
    }

    override fun getTypeParameters(): Array<out PsiTypeParameter?> {
        TODO("Not yet implemented")
    }

}