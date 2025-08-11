package io.github.potjerodekool.nabuidea.language.psi.impl

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.util.NlsSafe
import com.intellij.psi.*
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry
import io.github.potjerodekool.nabuidea.NabuFileType
import io.github.potjerodekool.nabuidea.NabuLanguage
import javax.swing.Icon

class NabuFileImpl(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, NabuLanguage),
    PsiClassOwner,
    NavigatablePsiElement
{
    override fun getFileType(): FileType {
        node
        return NabuFileType
    }

    override fun toString(): String {
        return "Nabu File"
    }

    override fun getPresentation(): ItemPresentation? {
        val file = this

        return object : ItemPresentation {

            override fun getPresentableText(): @NlsSafe String? {
                return file.name
            }

            override fun getIcon(p0: Boolean): Icon? {
                return file.getIcon(0)
            }

        }
    }

    override fun getReferences(): Array<out PsiReference?> {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this)
    }

    override fun getClasses(): Array<out PsiClass?> {
        return emptyArray()
    }

    override fun getPackageName(): @NlsSafe String? {
        val packageDeclaration = findPackageDeclaration()
        return packageDeclaration?.qualifiedName
    }

    override fun setPackageName(newPackageName: String) {
        val packageDeclaration = findPackageDeclaration()
    }

    private fun findPackageDeclaration(): NabuPackageDeclaration? {
        return findCompilationUnit()?.findPackageDeclaration()
    }

    private fun findCompilationUnit(): NabuCompilationUnit? {
        val start = findChildByClass(NabuStart::class.java)
        return start?.findCompilationUnit()
    }

    fun getImportList(): List<NabuImportDeclaration> {
        return findCompilationUnit()?.findImports() ?: emptyList()
    }
}

