package com.technokratos.compose.localization.locales_override

import com.technokratos.compose.localization.locales.target
import com.technokratos.localization.core.TranslatableOverride

object Strings {
    init {
        TranslatableOverride("target", "Override", emptyMap())
    }
}

fun targetOverride() = TranslatableOverride(target, "Override", emptyMap())