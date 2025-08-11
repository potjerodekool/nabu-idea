package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.icons.AllIcons
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.util.NlsSafe
import com.intellij.openapi.util.Pair
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.PsiClass
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry
import com.intellij.psi.javadoc.PsiDocComment
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.ui.RowIcon
import io.github.potjerodekool.nabuidea.language.NabuPsiUtils
import io.github.potjerodekool.nabuidea.language.reference.NabuReference
import io.github.potjerodekool.nabuidea.language.NabuTypes
import io.github.potjerodekool.nabuidea.language.psi.util.NabuElementFactory
import io.github.potjerodekool.nabuidea.language.psi.util.NabuElementKind
import io.github.potjerodekool.nabuidea.language.structure.ElementType
import io.github.potjerodekool.nabuidea.language.structure.IconUtils.getModifierIcon
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode
import org.antlr.intellij.adaptor.psi.ScopeNode
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.Unmodifiable
import javax.swing.Icon

class NabuClassDeclaration(
    astNode: ASTNode,
    val kind: NabuElementKind,
    val elementType: IElementType
) : ANTLRPsiNode(astNode),
    ScopeNode,
    NabuNamedElement,
    PsiClass,
    NavigatablePsiElement {

    fun typeIdentifier(): NabuTypeIdentifier {
        return PsiTreeUtil.getChildOfType(
            this,
            NabuTypeIdentifier::class.java
        )!!
    }

    fun classModifiers(): List<NabuModifier> {
        return PsiTreeUtil.getChildrenOfTypeAsList(
            this,
            NabuModifier::class.java
        )
    }

    override fun resolve(element: PsiNamedElement?): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun getNameIdentifier(): PsiIdentifier {
        val childNode = getNamedIdentifierNode()!!
        return childNode.psi as PsiIdentifier
    }

    override fun getScope(): PsiElement? {
        return null
    }

    override fun isInheritor(p0: PsiClass, p1: Boolean): Boolean {
        return false
    }

    override fun isInheritorDeep(p0: PsiClass, p1: PsiClass?): Boolean {
        return false
    }

    override fun getContainingClass(): PsiClass? {
        return null
    }

    override fun getVisibleSignatures(): Collection<HierarchicalMethodSignature?> {
        return emptyList()
    }

    override fun getNamedIdentifierNode(): ASTNode? {
        return node.findChildByType(NabuTypes.TYPE_IDENTIFIER)
    }

    override fun getName(): String {
        val nameIdentifier = nameIdentifier
        return nameIdentifier.text
    }

    override fun setName(newName: @NlsSafe String): PsiElement? {
        val renamingFile = isRenamingFile()

        if (NabuPsiUtils.changeName(
                this,
                {
                    val newIdentifier = NabuElementFactory.createTypeIdentifier(
                        node.psi.project,
                        newName
                    )
                    return@changeName newIdentifier.node
                }
            )) {
            if (renamingFile) {
                val file = getFile()
                file.name = newName + ".nabu"
            }
        }

        return this
    }

    private fun getFile(): NabuFileImpl {
        var parent = parent

        while (parent !is NabuFileImpl) {
            parent = parent.parent
        }

        return parent
    }

    private fun isRenamingFile(): Boolean {
        var parent = parent

        while (parent !is NabuFileImpl && parent !is NabuPsiClass) {
            parent = parent.parent
        }

        if (parent is NabuFileImpl) {
            val fileName = parent.name
            val end = fileName.lastIndexOf('.')
            val simpleName = fileName.substring(0, end)
            return simpleName.equals(name)
        } else {
            return false
        }
    }


    override fun getPresentation(): ItemPresentation? {
        val classDeclaration = this

        return object : ItemPresentation {
            override fun getPresentableText(): @NlsSafe String? {
                return classDeclaration.typeIdentifier().text
            }

            override fun getIcon(p0: Boolean): Icon? {
                val modifiers = classDeclaration.classModifiers()
                val modifierIcon = getModifierIcon(modifiers, ElementType.CLASS)

                return if (modifierIcon != null) RowIcon(modifierIcon, AllIcons.Nodes.Method)
                else AllIcons.Nodes.Method
            }

        }
    }

    override fun getQualifiedName(): String? {
        val simpleName = name

        val compilationUnit = findCompilationUnit()
        val packageDeclaration = compilationUnit.findPackageDeclaration()

        if (packageDeclaration == null) {
            return simpleName
        } else {
            val packageName = packageDeclaration.qualifiedName
            return "$packageName.$simpleName"
        }
    }

    override fun isInterface(): Boolean {
        return false
    }

    override fun isAnnotationType(): Boolean {
        return false
    }

    override fun isEnum(): Boolean {
        return false
    }

    override fun getExtendsList(): PsiReferenceList? {
        return null
    }

    override fun getImplementsList(): PsiReferenceList? {
        return null
    }

    override fun getExtendsListTypes(): Array<out PsiClassType?> {
        return emptyArray()
    }

    override fun getImplementsListTypes(): Array<out PsiClassType?> {
        return emptyArray()
    }

    override fun getSuperClass(): PsiClass? {
        return null
    }

    override fun getInterfaces(): Array<out PsiClass?> {
        return emptyArray()
    }

    override fun getSupers(): Array<out PsiClass?> {
        return emptyArray()
    }

    override fun getSuperTypes(): Array<out PsiClassType?> {
        return emptyArray()
    }

    override fun getFields(): Array<out PsiField?> {
        return emptyArray()
    }

    override fun getMethods(): Array<out PsiMethod?> {
        return emptyArray()
    }

    override fun getConstructors(): Array<out PsiMethod?> {
        return emptyArray()
    }

    override fun getInnerClasses(): Array<out PsiClass?> {
        return emptyArray()
    }

    override fun getInitializers(): Array<out PsiClassInitializer?> {
        return emptyArray()
    }

    override fun getAllFields(): Array<out PsiField?> {
        return emptyArray()
    }

    override fun getAllMethods(): Array<out PsiMethod?> {
        return emptyArray()
    }

    override fun getAllInnerClasses(): Array<out PsiClass?> {
        return emptyArray()
    }

    override fun findFieldByName(
        p0: @NonNls String?,
        p1: Boolean
    ): PsiField? {
        return null
    }

    override fun findMethodBySignature(
        p0: PsiMethod,
        p1: Boolean
    ): PsiMethod? {
        return null
    }

    override fun findMethodsBySignature(
        p0: PsiMethod,
        p1: Boolean
    ): Array<out PsiMethod?> {
        return emptyArray()
    }

    override fun findMethodsByName(
        p0: @NonNls String?,
        p1: Boolean
    ): Array<out PsiMethod?> {
        return emptyArray()
    }

    override fun findMethodsAndTheirSubstitutorsByName(
        p0: @NonNls String,
        p1: Boolean
    ): @Unmodifiable List<Pair<PsiMethod?, PsiSubstitutor?>?> {
        return emptyList()
    }

    override fun getAllMethodsAndTheirSubstitutors(): @Unmodifiable List<Pair<PsiMethod?, PsiSubstitutor?>?> {
        return emptyList()
    }

    override fun findInnerClassByName(
        p0: @NonNls String?,
        p1: Boolean
    ): PsiClass? {
        return null
    }

    override fun getLBrace(): PsiElement? {
        return null
    }

    override fun getRBrace(): PsiElement? {
        return null
    }

    private fun findCompilationUnit(): NabuCompilationUnit {
        var element = this.parent

        while (element !is NabuCompilationUnit) {
            element = element.parent
        }

        return element
    }

    override fun getReferences(): Array<out PsiReference?> {
        val references = ReferenceProvidersRegistry.getReferencesFromProviders(this);
        return references
        //return arrayOf(NabuReference(this))
    }

    override fun clone(): Any {
        TODO("Not yet implemented")
    }

    override fun getModifierList(): PsiModifierList? {
        return null
    }

    override fun hasModifierProperty(p0: @NonNls String): Boolean {
        return false
    }

    override fun isDeprecated(): Boolean {
        return false
    }

    override fun getDocComment(): PsiDocComment? {
        return null
    }

    override fun hasTypeParameters(): Boolean {
        return false
    }

    override fun getTypeParameterList(): PsiTypeParameterList? {
        return null
    }

    override fun getTypeParameters(): Array<out PsiTypeParameter?> {
        return emptyArray()
    }

    override fun getReference(): PsiReference? {
        val qName = qualifiedName

        return if (qName != null)
                NabuReference(this, TextRange(0,0), qName)
        else null
    }

    fun getCompilationUnit(): NabuCompilationUnit? {
        var element = this.parent

        while (element != null && element !is NabuCompilationUnit) {
            element = element.parent
        }

        return element
    }

    /*
    override fun getParent(): PsiElement {
        var element = super.getParent()

        while (element !is NabuCompilationUnit) {
            element = element.parent
        }

        if (element is NabuCompilationUnit) {
            val packageDeclaration = element.findPackageDeclaration()

            if (packageDeclaration != null) {
                return packageDeclaration
            }
        }

        return super.getParent()
    }
    */

}