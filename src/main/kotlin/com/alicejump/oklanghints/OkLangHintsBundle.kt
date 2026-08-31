package com.alicejump.oklanghints

import com.intellij.DynamicBundle
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.PropertyKey

private const val BUNDLE = "messages.OkLangHintsBundle"

class OkLangHintsBundle private constructor() : DynamicBundle(BUNDLE) {
    companion object {
        private val INSTANCE = OkLangHintsBundle()

        @JvmStatic
        @Nls
        fun message(
            @PropertyKey(resourceBundle = BUNDLE) key: String,
            vararg params: Any,
        ): String = INSTANCE.getMessage(key, *params)
    }
}
