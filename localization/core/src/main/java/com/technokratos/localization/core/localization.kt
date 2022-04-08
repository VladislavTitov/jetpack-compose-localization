package com.technokratos.localization.core

import java.util.Locale

data class Localization(
    val locale: Locale,
    val strings: MutableMap<Int, String> = mutableMapOf(),
    val plurals: MutableMap<Int, Plural> = mutableMapOf()
)

val defaultLocalization: Localization = Localization(Locale.ENGLISH)

private val supportedLocales: MutableSet<Locale> = mutableSetOf()

val localizationMap = hashMapOf<Locale, Localization>()

fun registerSupportedLocales(vararg locales: Locale): Set<Locale> {
    locales.filter { it != Locale.ENGLISH }
        .forEach {
            if (supportedLocales.add(it)) {
                registerLocalizationForLocale(it)
            }
        }
    return supportedLocales + Locale.ENGLISH
}

private fun registerLocalizationForLocale(locale: Locale) {
    localizationMap[locale] = Localization(locale)
}