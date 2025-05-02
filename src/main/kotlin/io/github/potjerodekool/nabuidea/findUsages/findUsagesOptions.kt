package io.github.potjerodekool.nabuidea.findUsages

import com.intellij.find.findUsages.PersistentFindUsagesOptions
import com.intellij.openapi.project.Project


interface NabuMemberFindUsagesOptions {
    var searchExpected: Boolean
}

class NabuClassFindUsagesOptions(project: Project) : NabuMemberFindUsagesOptions, PersistentFindUsagesOptions(project) {
    val isCheckDeepInheritance: Boolean = false
    val isDerivedClasses: Boolean = false
    val isDerivedInterfaces: Boolean = false

    override var searchExpected: Boolean = true

    var searchConstructorUsages: Boolean = true

    override fun equals(other: Any?): Boolean {
        return super.equals(other) && other is NabuClassFindUsagesOptions && other.searchConstructorUsages == searchConstructorUsages
    }

    override fun hashCode(): Int {
        return 31 * super.hashCode() + if (searchConstructorUsages) 1 else 0
    }

    override fun setDefaults(project: Project) {
        TODO("Not yet implemented")
    }

    override fun storeDefaults(project: Project) {
        TODO("Not yet implemented")
    }
}