package com.anyascii.build

import com.ibm.icu.lang.UCharacterCategory
import com.ibm.icu.lang.UScript

fun greek() = Table()
        .then(greekMath())
        .then(Table("greek"))
        .then(Table("greek-extended"))
        .cased(codePoints(UScript.GREEK))

private val GREEK_MATH_LETTERS = (0x1d6a8..0x1d7cb).filter { it.category != UCharacterCategory.MATH_SYMBOL }

private fun greekMath() = Table("greek-math")
        .cased(codePoints(UScript.GREEK))
        .then(GREEK_MATH_LETTERS.normalize(NFKC))
        .transliterate()
        .retain(GREEK_MATH_LETTERS)
