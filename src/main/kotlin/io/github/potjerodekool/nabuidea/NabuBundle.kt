package io.github.potjerodekool.nabuidea

import com.intellij.DynamicBundle
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey

@NonNls
private const val BUNDLE = "messages.NabuBundle"

object NabuBundle : DynamicBundle(BUNDLE) {

    @Nls
    private fun String.withHtml(): String = "<html>$this</html>"

    @Nls
    @JvmStatic
    fun message(@NonNls @PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any): String = getMessage(key, *params)

    @Nls
    @JvmStatic
    fun htmlMessage(@NonNls @PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any): String =
        getMessage(key, *params).withHtml()

    @Nls
    @JvmStatic
    fun lazyMessage(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any): () -> String = { getMessage(key, *params) }
}