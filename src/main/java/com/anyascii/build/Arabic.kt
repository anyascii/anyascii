package com.anyascii.build

import com.ibm.icu.lang.UCharacter.UnicodeBlock.*

fun arabic() = Table("arabic")
        .then(Table("arabic-symbols"))
        .then(presentationForms())

private val PRESENTATION_FORM_CHARS = Table(mapOf(
        0x0627 to "a",
        0x064b to "an",
        0x064c to "un",
        0x064d to "in",
        0x0651 to "-",
        0x0652 to "."
))

private val PRESENTATION_FORM_BLOCKS =
        block(ARABIC_PRESENTATION_FORMS_A) +
        block(ARABIC_PRESENTATION_FORMS_B) +
        block(ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS)

private fun presentationForms() = PRESENTATION_FORM_BLOCKS.toTable {
    PRESENTATION_FORM_CHARS.transliterateAny(NFKC.normalize(it).removePrefix(" "))
}
