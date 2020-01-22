package com.hunterwb.anyascii.build

import com.ibm.icu.lang.UCharacter
import com.ibm.icu.text.Normalizer2
import com.ibm.icu.text.UnicodeSet
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

fun name(codePoint: Int): String = UCharacter.getName(codePoint)

fun String.toCodePoint(): Int = codePointAt(0).also { check(length == Character.charCount(it)) }

fun UnicodeSet.codePoints(): List<Int> = map { it.toCodePoint() }

fun codePoints(set: String): List<Int> = UnicodeSet("[:$set:]").codePoints()

fun lower(s: String): String = UCharacter.toLowerCase(s)

fun lower(codePoint: Int): Int = UCharacter.toLowerCase(codePoint)

fun upper(s: String): String = UCharacter.toUpperCase(s)

fun upper(codePoint: Int): Int = UCharacter.toUpperCase(codePoint)