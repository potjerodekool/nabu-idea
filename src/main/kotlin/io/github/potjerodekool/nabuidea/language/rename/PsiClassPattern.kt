package io.github.potjerodekool.nabuidea.language.rename

import com.intellij.patterns.*
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiField
import com.intellij.psi.PsiMethod
import com.intellij.psi.util.InheritanceUtil
import com.intellij.util.ProcessingContext
import org.jetbrains.annotations.NonNls

class PsiClassPattern : PsiMemberPattern<PsiClass, PsiClassPattern>(PsiClass::class.java) {

    fun inheritorOf(
        strict: Boolean,
        pattern: PsiClassPattern
    ): PsiClassPattern {
        return this.with(object : PatternCondition<PsiClass?>("inheritorOf") {
            override fun accepts(psiClass: PsiClass, context: ProcessingContext?): Boolean {
                return isInheritor(psiClass, pattern, context, !strict)
            }
        })
    }

    private fun isInheritor(
        psiClass: PsiClass?,
        pattern: ElementPattern<*>,
        matchingContext: ProcessingContext?,
        checkThisClass: Boolean
    ): Boolean {
        return if (psiClass == null) {
            false
        } else if (checkThisClass && pattern.accepts(psiClass, matchingContext)
            || isInheritor(psiClass.superClass, pattern, matchingContext, true)) {
            true
        } else {
            psiClass.interfaces.any { aClass ->
                isInheritor(aClass, pattern, matchingContext, true)
            }
        }
    }

    fun inheritorOf(strict: Boolean, className: String): PsiClassPattern {
        return this.with(object : PatternCondition<PsiClass?>("inheritorOf") {
            override fun accepts(psiClass: PsiClass, context: ProcessingContext?): Boolean {
                return InheritanceUtil.isInheritor(psiClass, strict, className)
            }
        })
    }

    fun isInterface(): PsiClassPattern {
        return this.with(object : PatternCondition<PsiClass?>("isInterface") {
            override fun accepts(psiClass: PsiClass, context: ProcessingContext?): Boolean {
                return psiClass.isInterface
            }
        })
    }

    fun isAnnotationType(): PsiClassPattern {
        return this.with(object : PatternCondition<PsiClass?>("isAnnotationType") {
            override fun accepts(psiClass: PsiClass, context: ProcessingContext?): Boolean {
                return psiClass.isAnnotationType
            }
        })
    }

    fun withMethod(
        checkDeep: Boolean,
        memberPattern: ElementPattern<out PsiMethod?>
    ): PsiClassPattern {
        return this.with(object : PatternCondition<PsiClass?>("withMethod") {
            override fun accepts(psiClass: PsiClass, context: ProcessingContext?): Boolean {
                return (if (checkDeep) psiClass.allMethods else psiClass.methods).any { method ->
                    memberPattern.accepts(method, context)
                }
            }
        })
    }

    fun withField(
        checkDeep: Boolean,
        memberPattern: ElementPattern<out PsiField?>
    ): PsiClassPattern {
        return this.with(object : PatternCondition<PsiClass?>("withField") {
            override fun accepts(psiClass: PsiClass, context: ProcessingContext?): Boolean {
                return (if (checkDeep) psiClass.allFields else psiClass.getFields())
                    .any { field -> memberPattern.accepts(field, context) }
            }
        })
    }

    fun nonAnnotationType(): PsiClassPattern {
        return this.with(object : PatternCondition<PsiClass?>("nonAnnotationType") {
            override fun accepts(psiClass: PsiClass, context: ProcessingContext?): Boolean {
                return !psiClass.isAnnotationType
            }
        })
    }

    fun withQualifiedName(@NonNls qname: String): PsiClassPattern {
        return this.with(
            PsiClassNamePatternCondition(
                StandardPatterns.string().equalTo(qname)
            )
        )
    }

    fun withQualifiedName(@NonNls qname: ElementPattern<String?>): PsiClassPattern {
        return this.with(PsiClassNamePatternCondition(qname))
    }
}