package com.anyascii.build

import com.ibm.icu.lang.UCharacter.UnicodeBlock

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

private fun presentationForms() = Table().apply {
    val cps = UnicodeBlock.ARABIC_PRESENTATION_FORMS_A.codePoints() +
            UnicodeBlock.ARABIC_PRESENTATION_FORMS_B.codePoints() +
            UnicodeBlock.ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS.codePoints() -
            0xfdfc

    for (cp in cps) {
        val s = NFKC.normalize(cp.asString()).removePrefix(" ")
        this[cp] = PRESENTATION_FORM_CHARS.transliterateAny(s)
    }
}
