package com.technokratos.scripts

import com.technokratos.localization.core.Plural
import com.technokratos.localization.core.defaultLocalization
import com.technokratos.localization.core.generateUID
import com.technokratos.localization.core.isPluralRuleRegistered
import com.technokratos.localization.core.localizationMap
import com.technokratos.localization.core.registerSupportedLocales
import java.util.Locale

object LocalizationBuilder {
    fun registerLocales(vararg locales: Locale): Set<Locale> {
        return registerSupportedLocales(*locales)
    }

    fun Translatable(
        name: String,
        defaultValue: String,
        localeToValue: Map<Locale, String>
    ) {
        val id = generateUID(name)
        defaultLocalization.strings[id] = defaultValue
        for ((locale, value) in localeToValue.entries) {
            val localization =
                localizationMap[locale] ?: throw RuntimeException("There is no locale $locale")
            localization.strings[id] = value
        }
    }

    fun NonTranslatable(
        name: String,
        defaultValue: String
    ) {
        val id = generateUID(name)
        defaultLocalization.strings[id] = defaultValue
    }

    fun Plurals(
        name: String,
        defaultValue: Plural,
        localeToPlural: Map<Locale, Plural>
    ){
        val id = generateUID(name)
        defaultLocalization.plurals[id] = defaultValue
        for ((locale, value) in localeToPlural.entries) {
            if (!isPluralRuleRegistered(locale)) {
                // TODO issue-9
                println(
                    "jcl10n: " +
                            "There is no plural rule for $locale currently. Plural's 'other = ${value.other}' field can be used only!"
                )
            }
            val localization =
                localizationMap[locale] ?: throw RuntimeException("There is no locale $locale")
            localization.plurals[id] = value
        }
    }
}