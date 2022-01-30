package com.anyascii.build

import com.ibm.icu.impl.CaseMapImpl
import com.ibm.icu.lang.UCharacter
import com.ibm.icu.lang.UCharacter.UnicodeBlock
import com.ibm.icu.lang.UScript
import com.ibm.icu.util.ULocale
import kotlin.streams.asSequence

typealias CodePoint = Int

fun CodePoint(s: String): CodePoint = s.codePoints().asSequence().single()

fun String(codePoint: CodePoint): String = Character.toString(codePoint)

fun String(codePoints: IntArray) = String(codePoints, 0, codePoints.size)

val CodePoint.name: String get() = UCharacter.getName(this)

val CodePoint.nameAlias: String get() = UCharacter.getNameAlias(this) ?: name

fun codePointFromName(name: String): CodePoint? = UCharacter.getCharFromExtendedName(name).takeUnless { it == -1 }

fun String.title(): String = UCharacter.toTitleCase(ULocale.ROOT, this, null, CaseMapImpl.TITLECASE_WHOLE_STRING or UCharacter.TITLECASE_NO_BREAK_ADJUSTMENT)

fun String.lower(): String = UCharacter.toLowerCase(ULocale.ROOT, this)

fun CodePoint.lower(): CodePoint = UCharacter.toLowerCase(this)

fun String.upper(): String = UCharacter.toUpperCase(ULocale.ROOT, this)

fun CodePoint.upper(): CodePoint = UCharacter.toUpperCase(this)

val CodePoint.numericValue: Double? get() = UCharacter.getUnicodeNumericValue(this).takeUnless { it == UCharacter.NO_NUMERIC_VALUE }

val CodePoint.script: Int get() = UScript.getScript(this)

val CodePoint.category: Byte get() = UCharacter.getType(this).toByte()

val CodePoint.block: UnicodeBlock get() = UnicodeBlock.of(this)

fun String.codePointsArray(): IntArray = codePoints().toArray()

val CodePoint.plane get() = this ushr 16

fun String.stripLeading(c: Char) = dropWhile { it == c }

fun String.remove(s: String) = replace(s, "")

fun parseUCodePoint(s: String): CodePoint {
    require(s.startsWith("U+"))
    return s.substring(2).toInt(16)
}

fun Set<CodePoint>.containsAll(s: String) = s.codePoints().allMatch { it in this }

fun String.filter(predicate: (CodePoint) -> Boolean) = String(codePoints().filter(predicate).toArray())
