package io.github.potjerodekool.nabuidea.refactoring

import com.intellij.DynamicBundle
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey
import java.util.function.Supplier

object NabuRefactoringBundle {

    private val BUNDLE :  @NonNls String = "messages.NabuRefactoringBundle"
    private val INSTANCE = DynamicBundle(NabuRefactoringBundle::class.java, BUNDLE)

    fun message(key: @PropertyKey(resourceBundle = "messages.NabuRefactoringBundle") String, vararg params: Any): @Nls String {
        return INSTANCE.getMessage(key, params)
    }

    fun messagePointer(key: @PropertyKey(resourceBundle = "messages.NabuRefactoringBundle") String, vararg params: Any): Supplier<@Nls String> {
        return INSTANCE.getLazyMessage(key, params)
    }
}