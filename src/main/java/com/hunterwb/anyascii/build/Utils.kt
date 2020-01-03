package com.hunterwb.anyascii.build

import com.ibm.icu.text.Normalizer2
import java.util.regex.Pattern

fun <K, V> MutableMap<K, V>.putAllIfAbsent(other: Map<K, V>) {
    for ((k, v) in other) putIfAbsent(k, v)
}

fun <A, B> Pair<A, B>.swap(): Pair<B, A> = second to first

fun String.isAscii() = all { it.toInt().isAscii() }

fun Int.isAscii() = this in 0..127

private val CDM = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")

fun String.removeDiacritics(): String = CDM.matcher(Normalizer2.getNFDInstance().normalize(this)).replaceAll("")

fun toString(codePoint: Int): String = Character.toString(codePoint)