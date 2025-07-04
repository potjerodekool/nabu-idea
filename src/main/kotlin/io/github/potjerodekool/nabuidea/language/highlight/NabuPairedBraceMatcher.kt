package io.github.potjerodekool.nabuidea.language.highlight

import com.intellij.codeInsight.highlighting.PairedBraceAndAnglesMatcher
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.ParentAwareTokenSet
import io.github.potjerodekool.nabuidea.NabuFileType
import io.github.potjerodekool.nabuidea.NabuLanguage
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuTokenType

class NabuPairedBraceMatcher : PairedBraceAndAnglesMatcher(
    NabuBraceMatcher(), NabuLanguage, NabuFileType, Holder.TYPE_TOKENS
) {

    class Holder {
        companion object {
            val TYPE_TOKENS: ParentAwareTokenSet = ParentAwareTokenSet.orSet(
                ParentAwareTokenSet.create(
                    NabuTokenType.IDENTIFIER, NabuTokenType.COMMA,
                    NabuTokenType.AT,  //anno
                    NabuTokenType.RBRACKET, NabuTokenType.LBRACKET,  //arrays
                    NabuTokenType.QUEST, NabuTokenType.EXTENDS_KEYWORD, NabuTokenType.SUPER_KEYWORD
                )
            ) //wildcards
        }
    }

    override fun lt(): IElementType {
        TODO("Not yet implemented")
    }

    override fun gt(): IElementType {
        TODO("Not yet implemented")
    }
}