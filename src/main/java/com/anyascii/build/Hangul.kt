package com.anyascii.build

import com.ibm.icu.lang.UCharacter.UnicodeBlock.*

fun hangul() = Table("hangul")
        .finals()
        .variants()
        .syllables()
        .then(block(HANGUL_COMPATIBILITY_JAMO).normalize(::dm))

private fun Table.finals() = apply {
    for (cp in JAMO) {
        if (cp in this) continue
        val name = cp.nameAlias
        if ("JONGSEONG" in name) {
            val initial = codePointFromName(name.replace("JONGSEONG", "CHOSEONG")) ?: continue
            this[cp] = this[initial] ?: continue
        }
    }
}

private fun Table.variants() = apply {
    for (cp in JAMO) {
        if (cp in this) continue
        val name = cp.nameAlias.split(' ')
        val position = name[1]
        this[cp] = name[2].split('-').joinToString("") { letter(position, it) }
    }
}

private fun Table.letter(position: String, name: String): String {
    if (name == "FILLER") return ""
    val cp = checkNotNull(codePointFromName("HANGUL $position $name"))
    this[cp]?.let { return it }
    if (name.startsWith("KAPYEOUN")) return letter(position, name.removePrefix("KAPYEOUN")) + 'h'
    if (name.startsWith("SSANG")) return letter(position, name.removePrefix("SSANG")).repeat(2)
    if (name.startsWith("CHITUEUM")) return letter(position, name.removePrefix("CHITUEUM"))
    if (name.startsWith("CEONGCHIEUM")) return letter(position, name.removePrefix("CEONGCHIEUM"))
    error("$position $name")
}

private fun Table.syllables() = then(block(HANGUL_SYLLABLES).toTable { checkNotNull(transliterate(nfd(it))).title() })

private val JAMO = block(HANGUL_JAMO) + block(HANGUL_JAMO_EXTENDED_A) + block(HANGUL_JAMO_EXTENDED_B)
