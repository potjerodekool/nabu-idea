package io.github.potjerodekool.nabuidea.findUsages

import com.intellij.find.impl.HelpID
import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.tree.TokenSet
import com.intellij.util.indexing.IndexingBundle
import io.github.potjerodekool.nabu.NabuLexer
import io.github.potjerodekool.nabuidea.NabuLanguage
import io.github.potjerodekool.nabuidea.language.NabuTokenSets
import io.github.potjerodekool.nabuidea.language.psi.NabuNamedElement
import io.github.potjerodekool.nabuidea.language.psi.NabuPsiClass
import io.github.potjerodekool.nabuidea.language.psi.util.NabuElementKind
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NonNls


//TODO See JavaFindUsagesProvider
class NabuFindUsagesProvider : FindUsagesProvider {

    override fun getWordsScanner(): WordsScanner? {
        return DefaultWordsScanner(
            ANTLRLexerAdaptor(NabuLanguage, NabuLexer(null)),
            NabuTokenSets.IDENTIFIERS,
            NabuTokenSets.COMMENTS,
            TokenSet.EMPTY
        )
    }

    override fun canFindUsagesFor(psiElement: PsiElement): Boolean {
        return psiElement is PsiNamedElement
    }

    override fun getHelpId(element: PsiElement): @NonNls String? {
        //TODO
        if (element is NabuPsiClass) {
            return HelpID.FIND_CLASS_USAGES
        } else {
            return com.intellij.lang.HelpID.FIND_OTHER_USAGES;
        }
    }

    override fun getType(element: PsiElement): @Nls String {
        if (element is PsiFile) {
            return IndexingBundle.message("terms.file")
        }

        val kind = NabuElementKind.fromElement(element)

        if (kind != null) {
            return kind.type
        }

        //TODO
        return ""
    }

    override fun getDescriptiveName(element: PsiElement): @Nls String {
        if (element is NabuPsiClass) {
            val qName = element.getQualifiedName()
            return qName ?: (element.name ?: "")
        }

        if (element is NabuNamedElement) {
            return element.name ?: ""
        } else {
            return element.toString()
        }
    }

    override fun getNodeText(
        element: PsiElement,
        useFullName: Boolean
    ): @Nls String {
        if (element is NabuPsiClass) {
            var name = element.getQualifiedName()

            if (name == null || !useFullName) {
                name = element.name
            }

            if (name != null) {
                return name
            }
        }

        //TODO Check for more element types
        return ""
    }
}