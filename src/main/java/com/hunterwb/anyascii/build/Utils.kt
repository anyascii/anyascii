package com.hunterwb.anyascii.build

import com.ibm.icu.lang.UCharacter
import com.ibm.icu.lang.UScript
import com.ibm.icu.text.Normalizer2
import com.ibm.icu.text.UnicodeSet
import java.nio.file.Files
import java.nio.file.Path
import java.util.regex.Pattern

fun <K, V> MutableMap<K, V>.putAllIfAbsent(other: Map<K, V>) {
    for ((k, v) in other) putIfAbsent(k, v)
}

fun String.isPrintableAscii() = all { it.toInt() in 0x20..0x7e }

fun Int.isAscii() = this in 0..127

private val CDM = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")

fun String.removeDiacritics(): String = CDM.matcher(NFD.normalize(this)).replaceAll("")

fun toString(codePoint: Int): String = Character.toString(codePoint)

fun name(codePoint: Int): String = UCharacter.getName(codePoint)

fun codePoint(name: String): Int = UCharacter.getCharFromName(name)

fun String.toCodePoint(): Int = codePointAt(0).also { check(length == Character.charCount(it)) { this } }

fun UnicodeSet.codePoints(): List<Int> = map { it.toCodePoint() }

fun codePoints(set: String): List<Int> = UnicodeSet("[:$set:]").codePoints()

fun lower(s: String): String = UCharacter.toLowerCase(s)

fun lower(codePoint: Int): Int = UCharacter.toLowerCase(codePoint)

fun upper(s: String): String = UCharacter.toUpperCase(s)

fun upper(codePoint: Int): Int = UCharacter.toUpperCase(codePoint)

val NFKC: Normalizer2 = Normalizer2.getNFKCInstance()

val NFKD: Normalizer2 = Normalizer2.getNFKDInstance()

val NFD: Normalizer2 = Normalizer2.getNFDInstance()

fun numericValue(codePoint: Int): Int = UCharacter.getNumericValue(codePoint).also { check(it >= 0) }

val ROMAN_NUMERALS = mapOf(1 to "I", 5 to "V", 10 to "X", 50 to "L", 100 to "C")

fun isDefined(codePoint: Int) = UCharacter.isDefined(codePoint)

inline fun forEachLine(file: Path, f: (String) -> Unit) {
    Files.newBufferedReader(file).use { reader ->
        while (true) {
            val line = reader.readLine() ?: break
            f(line)
        }
    }
}

fun script(codePoint: Int): Int = UScript.getScript(codePoint)

fun String.codePointsArray(): IntArray = codePoints().toArray()