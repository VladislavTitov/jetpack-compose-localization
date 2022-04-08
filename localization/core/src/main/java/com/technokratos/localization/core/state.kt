package com.technokratos.localization.core

import java.util.Locale

class State(
    val defaultLocalization: Localization = Localization(Locale.ENGLISH),

    val supportedLocales: MutableSet<Locale> = mutableSetOf(),

    val localizationMap: MutableMap<Locale, Localization> = hashMapOf(),

    val nameToIdMap : MutableMap<String, Int> = hashMapOf()
) {
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
}