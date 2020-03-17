package com.hunterwb.anyascii.build

fun hangul() = Table()
        .then(icu("[:^Hang:]>; ::Any-Latin; ::Latin-ASCII; [:^ASCII:]>;"))
        .then(Table("hangul"))
        .normalize(NFKD)
        .combinations()

private fun Table.combinations() = apply {
    codePoints("Hang").forEach { cp ->
        if (cp in this) return@forEach
        val split = name(cp).split(' ')
        when (val position = split[1]) {
            "CHOSEONG", "JUNGSEONG", "JONGSEONG" -> {
                val letterNames = split[2].split('-')
                this[cp] = letterNames.joinToString("") { letter(position, it) }
            }
        }
    }
}

private fun Table.letter(position: String, name: String): String {
    return get(codePoint("HANGUL $position $name")) ?: getValue(codePoint("HANGUL LETTER $name"))
}