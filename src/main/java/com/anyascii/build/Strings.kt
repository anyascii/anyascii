package com.anyascii.build

import com.ibm.icu.lang.UCharacter
import com.ibm.icu.lang.UProperty
import com.ibm.icu.lang.UScript
import com.ibm.icu.text.UnicodeSet
import java.util.regex.Pattern

typealias CodePoint = Int

fun String.isPrintableAscii() = all { it.toInt() in 0x20..0x7e }

fun CodePoint.isAscii() = this in 0..127

fun CodePoint.asString(): String = Character.toString(this)

val CodePoint.name: String get() = UCharacter.getNameAlias(this) ?: UCharacter.getName(this)

fun CodePoint(name: String): CodePoint = UCharacter.getCharFromName(name)

fun codePoint(name: String): CodePoint? = UCharacter.getCharFromName(name).takeUnless { it == -1 }

fun String.toCodePoint(): CodePoint = codePointAt(0).also { check(length == Character.charCount(it)) { this } }

fun String.lower(): String = UCharacter.toLowerCase(this)

fun CodePoint.lower(): CodePoint = UCharacter.toLowerCase(this)

fun String.upper(): String = UCharacter.toUpperCase(this)

fun CodePoint.upper(): CodePoint = UCharacter.toUpperCase(this)

val CodePoint.numericValue: Int get() = UCharacter.getNumericValue(this).also { check(it >= 0) }

val CodePoint.num: String get() = numericValue.toString()

val CodePoint.float: Double get() = UCharacter.getUnicodeNumericValue(this).also { check(it != UCharacter.NO_NUMERIC_VALUE) }

val CodePoint.isDefined get() = UCharacter.isDefined(this)

val CodePoint.script: Int get() = UScript.getScript(this)

val CodePoint.category: Byte get() = UCharacter.getIntPropertyValue(this, UProperty.GENERAL_CATEGORY).toByte()

fun String.codePointsArray(): IntArray = codePoints().toArray()

private val CDM = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")

fun String.removeDiacritics(): String = CDM.matcher(NFD.normalize(this)).replaceAll("")

fun UnicodeSet.codePoints(): List<CodePoint> = map { it.toCodePoint() }

fun codePoints(): List<CodePoint> = UnicodeSet.ALL_CODE_POINTS.codePoints()

fun codePoints(set: String): List<CodePoint> = UnicodeSet("[:$set:]").codePoints()

fun Iterable<CodePoint>.filterDefined() = filter { it.isDefined }

inline fun Iterable<CodePoint>.filterName(predicate: (String) -> Boolean) = filter { predicate(it.name) }

fun String.stripLeading(c: Char) = dropWhile { it == c }

fun String.remove(s: String) = replace(s, "")
