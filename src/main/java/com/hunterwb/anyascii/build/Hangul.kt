package com.hunterwb.anyascii.build

fun hangul() = Table()
        .then(icu("[:^Hang:]>; ::Any-Latin; ::Latin-ASCII; [:^ASCII:]>;"))
        .then(Table("hangul"))
        .normalize(NFKD)
        .combinations()

private fun Table.combinations() = apply {
    codePoints("Hang").forEach { cp ->
        if (cp in this) return@forEach
        val split = cp.name.split(' ')
        when (val position = split[1]) {
            "CHOSEONG", "JUNGSEONG", "JONGSEONG" -> {
                val letterNames = split[2].split('-')
                this[cp] = letterNames.joinToString("") { letter(position, it) }
            }
        }
    }
}

private fun Table.letter(position: String, name: String): String {
    return get(CodePoint("HANGUL $position $name")) ?: getValue(CodePoint("HANGUL LETTER $name"))
}