package com.hunterwb.anyascii.build

fun ethiopic() = Table("ethiopic")
        .then(codePoints("Ethi").filterName { "SYLLABLE" in it }.toTable { syllableName(it) })
        .then((0x1372..0x137c).toTable { it.numericValue.toString() })

private fun syllableName(cp: CodePoint): String {
    var name = cp.name.lower()
            .replace("glottal ", "'")
            .replace("pharyngeal ", "`")

    for (x in WA_TO_WAA) {
        if (name.endsWith("syllable ${x}wa")) {
            name = "${name}a"
            break
        }
    }

    name = name.substringAfterLast(' ')

    for ((c, r) in CONSONANTS) {
        if (name.startsWith(c)) {
            return r + VOWELS.getValue(name.substringAfter(c))
        }
    }
    error(cp.name)
}

private val WA_TO_WAA = listOf("l", "hh", "m", "sz", "r", "s", "sh", "b", "v", "t", "c", "n", "ny", "'", "z", "zh", "d", "dd", "j", "th", "ch", "ph", "ts", "f", "p")

private val CONSONANTS = mapOf(
        "h" to "h",
        "l" to "l",
        "hh" to "h",
        "m" to "m",
        "sz" to "s",
        "r" to "r",
        "s" to "s",
        "sh" to "sh",
        "q" to "k'",
        "qh" to "kh'",
        "b" to "b",
        "v" to "v",
        "t" to "t",
        "c" to "ch",
        "x" to "h",
        "n" to "n",
        "ny" to "ny",
        "'" to "'",
        "k" to "k",
        "kx" to "h",
        "w" to "w",
        "`" to "`",
        "z" to "z",
        "zh" to "zh",
        "y" to "y",
        "d" to "d",
        "dd" to "d'",
        "j" to "j",
        "g" to "g",
        "gg" to "ng",
        "th" to "t'",
        "ch" to "ch'",
        "ph" to "p'",
        "ts" to "ts'",
        "tz" to "ts'",
        "f" to "f",
        "p" to "p",
        "ss" to "sh",
        "cc" to "ch",
        "zz" to "zh",
        "cch" to "ch'",
        "qy" to "k'y",
        "ky" to "ky",
        "xy" to "xy",
        "gy" to "gy",
        "tth" to "th",
        "ddh" to "dh",
        "dz" to "dz",
        "cchh" to "ch'",
        "bb" to "b'"
).toSortedMap(compareBy({ -it.length }, { it }))

private val VOWELS = mapOf(
        "a" to "e",
        "u" to "u",
        "i" to "i",
        "aa" to "a",
        "ee" to "ie",
        "e" to "",
        "o" to "o",

        "wa" to "we",
        // wu
        "wi" to "wi",
        "waa" to "wa",
        "wee" to "wie",
        "we" to "wi",
        // wo

        "ya" to "ya",
        "oa" to "oa"
)