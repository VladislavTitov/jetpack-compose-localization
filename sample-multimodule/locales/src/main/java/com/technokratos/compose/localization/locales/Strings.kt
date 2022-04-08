package com.technokratos.compose.localization.locales

import com.technokratos.localization.core.Translatable
import com.technokratos.localization.core.registerSupportedLocales
import java.util.Locale

val RUSSIAN = Locale("ru")
val TATAR = Locale("tt")

val supportedLocalesNow = registerSupportedLocales(RUSSIAN, TATAR)

val target = Translatable("target", "Origin", emptyMap())