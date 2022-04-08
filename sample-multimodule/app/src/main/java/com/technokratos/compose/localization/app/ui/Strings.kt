package com.technokratos.compose.localization.app.ui

import com.technokratos.localization.core.NonTranslatable
import com.technokratos.localization.core.TranslatableOverride

val stringFromOverride = NonTranslatable("Fuck")

object Strings {
    init {
        TranslatableOverride("target", "Override in app", emptyMap())
    }
}