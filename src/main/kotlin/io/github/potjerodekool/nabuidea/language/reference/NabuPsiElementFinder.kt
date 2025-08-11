package io.github.potjerodekool.nabuidea.language.reference

import com.intellij.openapi.application.ReadActionProcessor
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.PackageIndex
import com.intellij.openapi.util.Condition
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.Processor
import com.jetbrains.rd.util.LogLevel
import com.jetbrains.rd.util.getLogger
import io.github.potjerodekool.nabuidea.language.NabuUtils
import io.github.potjerodekool.nabuidea.language.psi.impl.NabuClassDeclaration
import java.util.function.Predicate

class NabuPsiElementFinder(private val project: Project) : PsiElementFinder() {

    private fun findClasses(qualifiedName: String,
                            project: Project): List<PsiClass> {
        val classes = NabuUtils.findClassesByFilter(project) {
                element ->
            val clazz = element as NabuClassDeclaration
            val className = clazz.qualifiedName
            return@findClassesByFilter className == qualifiedName
        }
            .filter { it is PsiClass }
            .map { it as PsiClass }

        if (qualifiedName.startsWith("bar.") && classes.isEmpty()) {
            log("class ${qualifiedName} not found")
        }

        return classes
    }

    override fun findClass(
        qualifiedName: String,
        scope: GlobalSearchScope
    ): PsiClass? {
        log("findClass $qualifiedName")

        val project = scope.project

        if (project == null) {
            return null
        }

        val classes = findClasses(qualifiedName, project)

        if (qualifiedName.startsWith("bar.") && classes.isEmpty()) {
            log("class ${qualifiedName} not found")
        }

        return if (classes.size == 1) classes.first()
            else null
    }

    private fun log(message: String) {
        getLogger<NabuPsiElementFinder>().log(
            LogLevel.Debug,
            message,
            null
        )
    }

    override fun findClasses(
        qualifiedName: String,
        scope: GlobalSearchScope
    ): Array<out PsiClass?> {
        log("findClasses $qualifiedName")

        val project = scope.project

        if (project == null) {
            return emptyArray()
        }

        return findClasses(
            qualifiedName,
            project
        ).toTypedArray()
    }

    override fun findPackage(qualifiedName: String): PsiPackage? {
        log("findPackage $qualifiedName")

        val p = super.findPackage(qualifiedName)

        if (p == null) {
            log("package ${qualifiedName} not found")
        }

        return p
    }

    override fun processPackageDirectories(
        psiPackage: PsiPackage,
        scope: GlobalSearchScope,
        consumer: Processor<in PsiDirectory>
    ): Boolean {
        log("processPackageDirectories ${psiPackage.qualifiedName}")

        val psiManager = PsiManager.getInstance(this.project)
        return PackageIndex.getInstance(this.project)
            .getDirsByPackageName(psiPackage.getQualifiedName(), false)
            .forEach(object : ReadActionProcessor<VirtualFile?>() {

                override fun processInReadAction(dir: VirtualFile?): Boolean {
                    if (dir != null && scope.contains(dir)) {
                        val psiDir = psiManager.findDirectory(dir)
                        if (psiDir != null && !consumer.process(psiDir)) {
                            return false
                        }
                    }

                    return true
                }
            })
    }


    override fun processPackageDirectories(
        psiPackage: PsiPackage,
        scope: GlobalSearchScope,
        consumer: Processor<in PsiDirectory>,
        includeLibrarySources: Boolean
    ): Boolean {
        log("processPackageDirectories ${psiPackage.qualifiedName}")

        val psiManager = PsiManager.getInstance(this.project)
        return PackageIndex.getInstance(this.project)
            .getDirsByPackageName(psiPackage.getQualifiedName(), false)
            .forEach(object : ReadActionProcessor<VirtualFile?>() {

                override fun processInReadAction(dir: VirtualFile?): Boolean {
                    if (dir != null && scope.contains(dir)) {
                        val psiDir = psiManager.findDirectory(dir)
                        if (psiDir != null && !consumer.process(psiDir)) {
                            return false
                        }
                    }

                    return true
                }
            })
    }

    override fun hasClass(
        qualifiedName: String,
        scope: GlobalSearchScope,
        filter: Predicate<PsiClass?>
    ): Boolean {
        log("hasClass $qualifiedName")
        return super.hasClass(qualifiedName, scope, filter)
    }

    override fun getSubPackages(
        psiPackage: PsiPackage,
        scope: GlobalSearchScope
    ): Array<out PsiPackage?> {
        log("getSubPackages ${psiPackage.qualifiedName}")
        return super.getSubPackages(psiPackage, scope)
    }

    override fun getClasses(
        psiPackage: PsiPackage,
        scope: GlobalSearchScope
    ): Array<out PsiClass?> {
        log("getClasses ${psiPackage.qualifiedName}")
        return super.getClasses(psiPackage, scope)
    }

    override fun getClasses(
        className: String?,
        psiPackage: PsiPackage,
        scope: GlobalSearchScope
    ): Array<out PsiClass?> {
        log("getClasses ${psiPackage.qualifiedName}")
        return super.getClasses(className, psiPackage, scope)
    }

    override fun getClassesFilter(scope: GlobalSearchScope): Predicate<PsiClass?>? {
        log("getClassesFilter")
        return super.getClassesFilter(scope)
    }

    override fun getPackageFiles(
        psiPackage: PsiPackage,
        scope: GlobalSearchScope
    ): Array<out PsiFile?> {
        log("getPackageFiles ${psiPackage.qualifiedName}")
        return super.getPackageFiles(psiPackage, scope)
    }

    override fun getPackageFilesFilter(
        psiPackage: PsiPackage,
        scope: GlobalSearchScope
    ): Condition<PsiFile?>? {
        log("getPackageFilesFilter ${psiPackage.qualifiedName}")
        return super.getPackageFilesFilter(psiPackage, scope)
    }

    override fun getClassNames(
        psiPackage: PsiPackage,
        scope: GlobalSearchScope
    ): Set<String?> {
        log("getClassNames ${psiPackage.qualifiedName}")
        return super.getClassNames(psiPackage, scope)
    }
}