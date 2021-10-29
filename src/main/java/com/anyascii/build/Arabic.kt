package com.anyascii.build

import com.ibm.icu.lang.UCharacter.UnicodeBlock

fun arabic() = Table("arabic")
        .forms(UnicodeBlock.ARABIC_PRESENTATION_FORMS_A.codePoints())
        .forms(UnicodeBlock.ARABIC_PRESENTATION_FORMS_B.codePoints())
        .minus(0xfdfc)

private val PRESENTATION_FORMS = Table(mapOf(
        0x0627 to "a",
        0x064b to "an",
        0x064c to "un",
        0x064d to "in",
        0x0651 to "-",
        0x0652 to "."
))

private fun Table.forms(codePoints: Iterable<CodePoint>): Table = apply {
    for (cp in codePoints) {
        if (cp in this) continue
        val a = NFKC.normalize(cp.asString()).trim()
        val b = PRESENTATION_FORMS.transliterateAny(a)
        val c = transliterateAny(b)
        if (c.isPrintableAscii()) this[cp] = c
    }
}