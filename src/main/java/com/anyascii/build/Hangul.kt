package com.anyascii.build

import com.ibm.icu.lang.UCharacter.UnicodeBlock
import com.ibm.icu.lang.UScript

fun hangul() = Table("hangul")
        .combinations()
        .syllables()

private fun Table.combinations() = apply {
    codePoints(UScript.HANGUL).forEach { cp ->
        if (cp in this) return@forEach
        val split = cp.name.split(' ')
        if (split.last() == "FILLER") {
            this[cp] = ""
            return@forEach
        }
        when (val position = split[1]) {
            "CHOSEONG", "JUNGSEONG", "JONGSEONG", "LETTER" -> {
                val letterNames = split[2].split('-')
                this[cp] = letterNames.joinToString("") { letter(position, it) }
            }
        }
    }
}

private fun Table.letter(position: String, name: String): String {
    get(CodePoint("HANGUL $position $name"))?.let { return it }
    get(CodePoint("HANGUL LETTER $name"))?.let { return it }
    if (name.startsWith("KAPYEOUN")) return letter(position, name.removePrefix("KAPYEOUN")) + 'h'
    if (name.startsWith("SSANG")) return letter(position, name.removePrefix("SSANG")).repeat(2)
    if (name.startsWith("CHITUEUM")) return letter(position, name.removePrefix("CHITUEUM"))
    if (name.startsWith("CEONGCHIEUM")) return letter(position, name.removePrefix("CEONGCHIEUM"))
    error("$position $name")
}

private fun Table.syllables() = then(UnicodeBlock.HANGUL_SYLLABLES.toTable {
    checkNotNull(transliterate(NFD.normalize(it.asString()))).capitalize()
})