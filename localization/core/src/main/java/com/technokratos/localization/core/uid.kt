package com.technokratos.localization.core

import java.util.concurrent.atomic.AtomicInteger

private val resUID = AtomicInteger(Int.MIN_VALUE)
private val objToUID = mutableMapOf<String, Int>()

fun generateUID(name: String): Int = objToUID[name] ?: resUID.incrementAndGet().also {
    objToUID[name] = it
}

fun getUID(name: String) = objToUID[name]
