package com.anyascii.build

import com.ibm.icu.lang.UCharacter.UnicodeBlock.*
import com.ibm.icu.lang.UCharacterCategory.*

fun greek() = Table()
        .then(greekMath())
        .then(Table("greek"))
        .then(Table("greek-extended"))
        .cased(block(GREEK))

private val GREEK_MATH_LETTERS = range(0x1d6a8..0x1d7cb) - category(MATH_SYMBOL)

private fun greekMath() = Table("greek-math")
        .cased(block(GREEK))
        .then(GREEK_MATH_LETTERS.normalize(::nfkc))
        .transliterate()
        .retain(GREEK_MATH_LETTERS)
