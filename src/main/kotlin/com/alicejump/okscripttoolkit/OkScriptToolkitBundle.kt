package com.alicejump.okscripttoolkit

import com.intellij.DynamicBundle
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.PropertyKey

private const val BUNDLE = "messages.OkScriptToolkitBundle"

class OkScriptToolkitBundle private constructor() : DynamicBundle(BUNDLE) {
    companion object {
        private val INSTANCE = OkScriptToolkitBundle()

        @JvmStatic
        @Nls
        fun message(
            @PropertyKey(resourceBundle = BUNDLE) key: String,
            vararg params: Any,
        ): String = INSTANCE.getMessage(key, *params)
    }
}
