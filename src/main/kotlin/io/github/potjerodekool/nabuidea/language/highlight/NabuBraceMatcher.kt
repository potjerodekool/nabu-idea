package io.github.potjerodekool.nabuidea.language.highlight

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuTokenType

class NabuBraceMatcher : PairedBraceMatcher {

    private val pairs = arrayOf<BracePair>(
        BracePair(NabuTokenType.LPARENTH, NabuTokenType.RPARENTH, false),
        BracePair(NabuTokenType.LBRACE, NabuTokenType.RBRACE, true),
        BracePair(NabuTokenType.LBRACKET, NabuTokenType.RBRACKET, false),
        //BracePair(JavaDocTokenType.DOC_INLINE_TAG_START, JavaDocTokenType.DOC_INLINE_TAG_END, false),
        BracePair(NabuTokenType.LT, NabuTokenType.GT, false),
        //BracePair(JavaDocTokenType.DOC_LBRACKET, JavaDocTokenType.DOC_RBRACKET, false),
        //BracePair(JavaDocTokenType.DOC_LPAREN, JavaDocTokenType.DOC_RPAREN, false)
    )

    override fun getPairs(): Array<out BracePair?> {
        TODO("Not yet implemented")
    }

    override fun isPairedBracesAllowedBeforeType(
        lbraceType: IElementType,
        contextType: IElementType?
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCodeConstructStart(file: PsiFile?, openingBraceOffset: Int): Int {
        TODO("Not yet implemented")
    }
}