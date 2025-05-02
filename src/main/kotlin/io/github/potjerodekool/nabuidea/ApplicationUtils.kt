package io.github.potjerodekool.nabuidea

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.Project

fun <T> runReadAction(action: () -> T): T {
    return ApplicationManager.getApplication().runReadAction<T>(action)
}

fun <T> Project.runReadActionInSmartMode(action: () -> T): T {
    if (ApplicationManager.getApplication().isReadAccessAllowed) return action()
    return DumbService.getInstance(this).runReadActionInSmartMode<T>(action)
}