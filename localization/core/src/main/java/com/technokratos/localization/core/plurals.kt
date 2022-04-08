package com.technokratos.localization.core

import java.util.Locale
import kotlin.math.absoluteValue

class PluralRule(
    val zero: (Double, Long, Long, Int, Int) -> Boolean = { _, _, _, _, _ -> false },
    val one: (Double, Long, Long, Int, Int) -> Boolean = { _, _, _, _, _ -> false },
    val two: (Double, Long, Long, Int, Int) -> Boolean = { _, _, _, _, _ -> false },
    val few: (Double, Long, Long, Int, Int) -> Boolean = { _, _, _, _, _ -> false },
    val many: (Double, Long, Long, Int, Int) -> Boolean = { _, _, _, _, _ -> false }
)

class Plural(
    val zero: CharSequence? = null,
    val one: CharSequence? = null,
    val two: CharSequence? = null,
    val few: CharSequence? = null,
    val many: CharSequence? = null,
    val other: CharSequence
)

private fun Localization.getPlural(name: Int, quantity: Double): CharSequence? {
    val absQuantity = quantity.absoluteValue
    val (int, frac) = absQuantity.toString().split('.')
    val integerPart = int.toLong()
    val fractionPart = frac.trimStart('0').ifEmpty { "0" }.toLong()
    val fractionPartDigitCount = frac.trimEnd('0').count()

    return plurals[name]?.let {
        resolveString(
            it,
            locale,
            absQuantity,
            integerPart,
            fractionPart,
            fractionPartDigitCount,
            0 // we don't support String quantity parameter now so exp part is always 0
        )
    }
}

private val defaultPluralRule = onlyOther

fun isPluralRuleRegistered(locale: Locale): Boolean {
    return localeToPluralRule.containsKey(locale)
}

private fun resolveString(
    plural: Plural,
    locale: Locale,
    n: Double,
    i: Long,
    f: Long,
    v: Int,
    e: Int
): CharSequence {
    val rule = localeToPluralRule[locale] ?: defaultPluralRule

    return when {
        rule.zero(n, i, f, v, e) && plural.zero != null -> plural.zero
        rule.one(n, i, f, v, e) && plural.one != null -> plural.one
        rule.two(n, i, f, v, e) && plural.two != null -> plural.two
        rule.few(n, i, f, v, e) && plural.few != null -> plural.few
        rule.many(n, i, f, v, e) && plural.many != null -> plural.many
        else -> plural.other
    }
}
