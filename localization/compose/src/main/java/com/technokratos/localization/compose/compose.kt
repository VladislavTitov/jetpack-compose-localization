package com.technokratos.localization.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import com.technokratos.localization.core.Localization
import com.technokratos.localization.core.defaultLocalization
import com.technokratos.localization.core.localizationMap
import java.util.Locale

val LocalLocalization = compositionLocalOf { defaultLocalization }

object Vocabulary {
    val localization: Localization
        @Composable
        @ReadOnlyComposable
        get() = LocalLocalization.current
}

@Composable
fun Localization(locale: Locale, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalLocalization provides (localizationMap[locale] ?: defaultLocalization),
        content = content
    )
}
