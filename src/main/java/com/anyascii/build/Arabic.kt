package com.anyascii.build

fun arabic() = Table("arabic")
        .then((0x10e60..0x10e7a).toTable { it.num })
        .forms(((0xfb50..0xfdff) + (0xfe70..0xfeff) - 0xfdfc).filterDefined())

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
        this[cp] = c
    }
}