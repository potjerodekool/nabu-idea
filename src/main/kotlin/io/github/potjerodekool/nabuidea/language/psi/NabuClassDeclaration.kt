package io.github.potjerodekool.nabuidea.language.psi

import com.intellij.icons.AllIcons
import com.intellij.lang.ASTNode
import com.intellij.model.psi.PsiSymbolReference
import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.util.NlsSafe
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.ui.RowIcon
import io.github.potjerodekool.nabuidea.language.NabuPsiUtils
import io.github.potjerodekool.nabuidea.language.NabuReference
import io.github.potjerodekool.nabuidea.language.NabuTypes
import io.github.potjerodekool.nabuidea.language.psi.util.NabuElementKind
import io.github.potjerodekool.nabuidea.language.psi.util.NabuElementFactory
import io.github.potjerodekool.nabuidea.language.structure.ElementType
import io.github.potjerodekool.nabuidea.language.structure.IconUtils.getModifierIcon
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode
import org.antlr.intellij.adaptor.psi.ScopeNode
import javax.swing.Icon

class NabuClassDeclaration(
    astNode: ASTNode,
    val kind: NabuElementKind
) : ANTLRPsiNode(astNode),
    ScopeNode,
    NabuNamedElement,
    NabuPsiClass,
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

    override fun getNameIdentifier(): PsiElement? {
        val childNode = getNamedIdentifierNode()
        return childNode?.psi
    }

    override fun getNamedIdentifierNode(): ASTNode? {
        return node.findChildByType(NabuTypes.TYPE_IDENTIFIER)
    }

    override fun getName(): String? {
        val nameIdentifier = nameIdentifier
        return nameIdentifier?.text
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

    private fun getFile(): NabuFile {
        var parent = parent

        while (parent !is NabuFile) {
            parent = parent.parent
        }

        return parent
    }

    private fun isRenamingFile(): Boolean {
        var parent = parent

        while (parent !is NabuFile && parent !is NabuPsiClass) {
            parent = parent.parent
        }

        if (parent is NabuFile) {
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

    private fun findCompilationUnit(): NabuCompilationUnit {
        var element = this.parent

        while (element !is NabuCompilationUnit) {
            element = element.parent
        }

        return element
    }

    override fun getReferences(): Array<out PsiReference?> {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this)
    }

}