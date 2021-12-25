package com.anyascii.build

import com.ibm.icu.impl.UCharacterProperty
import com.ibm.icu.lang.CharacterProperties
import com.ibm.icu.lang.UCharacter.UnicodeBlock
import com.ibm.icu.lang.UCharacterCategory.*
import com.ibm.icu.lang.UProperty
import com.ibm.icu.text.UnicodeSet

data class CodePointSet(private val us: UnicodeSet) : AbstractSet<CodePoint>(), (CodePoint) -> Boolean {

    operator fun plus(other: CodePointSet) = CodePointSet(UnicodeSet(us).addAll(other.us))

    operator fun minus(other: CodePointSet) = CodePointSet(UnicodeSet(us).removeAll(other.us))

    infix fun and(other: CodePointSet) = CodePointSet(UnicodeSet(us).retainAll(other.us))

    operator fun not() = CodePointSet(UnicodeSet(us).complement())

    override fun iterator(): Iterator<CodePoint> = iterator {
        for (r in us.ranges()) {
            for (cp in r.codepoint..r.codepointEnd) {
                yield(cp)
            }
        }
    }

    override val size get() = us.size()

    override fun isEmpty() = us.isEmpty

    override fun contains(element: CodePoint) = element in us

    override fun invoke(p1: CodePoint) = p1 in us
}

val ALL = !category(UNASSIGNED, PRIVATE_USE, SURROGATE)

val ASCII = block(UnicodeBlock.BASIC_LATIN)

val PRINTABLE_ASCII = ASCII - category(CONTROL)

val ASCII_DIGITS = ASCII and category(DECIMAL_DIGIT_NUMBER)

val ASCII_LETTERS = ASCII and category(UPPERCASE_LETTER, LOWERCASE_LETTER)

fun category(vararg category: Byte) = property(UProperty.GENERAL_CATEGORY_MASK, categoryMask(*category))

private fun categoryMask(vararg category: Byte) = category.fold(0) { acc, c -> acc + UCharacterProperty.getMask(c.toInt()) }

fun script(script: Int) = property(UProperty.SCRIPT, script)

fun block(block: Int) = property(UProperty.BLOCK, block) and ALL

fun block(block: UnicodeBlock) = block(block.id)

fun property(property: Int) = CodePointSet(CharacterProperties.getBinaryPropertySet(property))

fun property(property: Int, value: Int) = CodePointSet(UnicodeSet().applyIntPropertyValue(property, value))

fun range(range: IntRange) = CodePointSet(UnicodeSet(range.first, range.last)) and ALL
