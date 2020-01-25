package com.hunterwb.anyascii.build

import com.ibm.icu.text.Transliterator
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val table = ascii()
            .then(decimalDigits())
            .then(custom())
            .normalize(NFKC)
            .then(icu("::Latin-ASCII; [:^ASCII:]>;"))
            .then(unihan())
            .then(icu("[:^Han:]>; ::Han-Latin; ::Latin-ASCII; [:^ASCII:]>; ::Any-Title;"))
            .normalize(NFKC)
            .then(unidecode())
            .then(icu("::Any-Latin; ::Latin-ASCII; [:^ASCII:]>;"))
            .normalize(NFKC)
            .cased()
            .minus(ascii())
            .write("table.tsv")

    java(table)
    python(table)
    js(table)
}

private fun icu(rules: String): Table {
    val table = Table()
    val transliterator = Transliterator.createFromRules(rules, rules, Transliterator.FORWARD)
    for (cp in 128..Character.MAX_CODE_POINT) {
        val output = transliterator.transliterate(toString(cp))
        if (output.isNotEmpty()) {
            table[cp] = output
        }
    }
    return table
}

private fun unidecode() = Table("input/unidecode.tsv")

private fun unihan(): Table {
    return Table()
            .then(unihan("kMandarin"))
            .then(unihan("kCantonese"))
            .then(unihan("kVietnamese"))
            .then(unihan("kJapaneseOn"))
            .then(unihan("kJapaneseKun"))
            .then(unihan("kHanyuPinyin"))
            .then(unihan("kHangul"))
}

private fun unihan(key: String): Table {
    val table = Table()
    val reader = Files.newBufferedReader(Path.of("input/Unihan_Readings.txt"))
    reader.lineSequence()
            .filter { !it.startsWith("#") && it.isNotEmpty() }
            .map { it.split('\t', limit = 3) }
            .filter { it[1] == key }
            .forEach {
                val cp = Integer.parseInt(it[0].drop(2), 16)
                var output = it[2]
                when (key) {
                    "kHangul" -> {
                        output = output.substringBefore(':')
                        output = Transliterator.getInstance("Hangul-Latin").transliterate(output)
                    }
                    "kTang" -> {
                        output = output.removePrefix("*").split(' ')[0]
                    }
                    else -> {
                        output = output.substringAfter(':')
                        output = output.split(' ', ',')[0]
                        output = output.removeDiacritics()
                        if (output.last().isDigit()) output = output.dropLast(1)
                        output = output.toLowerCase().capitalize()
                        output = output.replace('Đ', 'D')
                    }
                }
                table[cp] = output
            }
    reader.close()
    return table
}

private fun ascii(): Table = (0..127).toTable { toString(it) }

private fun decimalDigits() = codePoints("Nd").toTable { numericValue(it).toString() }

private fun custom() = Table()
        .then(Table("input/nko.tsv"))
        .then(Table("input/math-symbols-a.tsv"))
        .then(Table("input/math-symbols-b.tsv"))
        .then((0xe0020..0xe007e).toTable { toString(it - 0xe0000) }) // tags
        .then((0x1f1e6..0x1f1ff).toTable { toString(it - 0x1f1e6 + 'A'.toInt()) }) // regional indicators
        .then(Table("input/han-misc.tsv"))
        .then(Table("input/kangxi-radicals.tsv"))
        .then(Table("input/cjk-radicals.tsv"))
        .then((0x31c0..0x31e3).toTable { name(it).substringAfterLast(' ') }) // cjk strokes
        .then((0x33e0..0x33fe).toTable { "${(it - 0x33e0 + 1)}D" }) // telegraph days
        .then((0x3358..0x3370).toTable { "${(it - 0x3358)}H" }) // telegraph hours
        .then((0x32c0..0x32cb).toTable { "${(it - 0x32c0 + 1)}M" }) // telegraph months
        .then((0x3220..0x3229).toTable { "(${(it - 0x3220 + 1)})" }) // parenthesized numbers
        .then((0x3280..0x3289).toTable { "(${(it - 0x3280 + 1)})" }) // circled numbers
        .then((0x3021..0x3029).toTable { "${(it - 0x3021 + 1)}" }) // hangzhou numerals
        .then(yi())
        .then(vai())
        .then(ethiopic())
        .then(dominoes())
        .then(Table("input/ocr.tsv"))
        .then(olChiki())
        .then(cyrillic())
        .then((0x24eb..0x24ff).toTable { numericValue(it).toString() }) // circled numbers
        .then(greek())

private fun cyrillic() = Table()
        .then(Table("input/cyrillic.tsv"))
        .cased()
        .normalize(NFKC)
        .aliasing((0xa674..0xa67b) + (0xa69e..0xa69f) + (0x2de0..0x2dff) - 0x2df5) { it.replace("COMBINING CYRILLIC", "CYRILLIC SMALL") }

private fun greek() = Table()
        // iota subscript?
        .then(Table("input/greek-symbols.tsv"))
        .then(greekMath())
        .then(Table("input/greek.tsv"))
        .cased()
        .minus(0x345)
        .apply {
            then(codePoints("Grek").filter { name(it).contains("WITH DASIA") }.toTable {
                val n = name(it).substringBefore(" WITH")
                val o = getValue(codePoint(n))
                if ("CAPITAL" in n) "H${lower(o)}" else "h$o"
            })
        }
        .normalize(NFKD, "")
        .aliasing((0x1d26..0x1d2a)) { it.replace("LETTER SMALL CAPITAL", "CAPITAL LETTER") }

private fun greekMath() = Table("input/greek-math.tsv")
        .cased()
        .normalize(NFKC)
        .retain((0x1d6a8..0x1d7cb) + 0x2207 + 0x2202 + 0x3f4 + 0x3f5 + 0x3d1 + 0x3f0 + 0x3d5 + 0x3f1 + 0x3d6 + 0x3d0)

private fun yi() = Table()
        .then(0xa015, "w")
        .then((0xa000..0xa48c).toTable { name(it).substringAfterLast(' ').toLowerCase() }) // syllables
        .then((0xa490..0xa4c6).toTable { name(it).substringAfterLast(' ') }) // radicals

private fun vai() = Table()
        .then(Table("input/vai.tsv"))
        .then((0xa500..0xa62b).toTable { name(it).substringAfterLast(' ').toLowerCase() })

private fun ethiopic() = Table()
        .then(codePoints("Ethi").filter { name(it).contains("SYLLABLE") }.toTable {
            val name = name(it).removePrefix("ETHIOPIC SYLLABLE ").removePrefix("SEBATBEIT ").toLowerCase()
            if (' ' in name) "'${name.substringAfterLast(' ')}" else name
        })

private fun olChiki() = Table()
        .then((0x1c5a..0x1c77).toTable {
            val name = name(it).substringAfterLast(' ').toLowerCase()
            if (name.startsWith('l')) name.substring(1) else name.replace("[aeiou]".toRegex(), "")
        })

private fun dominoes() = (0x1f030..0x1f093).toTable {
    val name = name(it).removePrefix("DOMINO TILE ")
    var s = name.take(1)
    if ("BACK" in name) {
        s += "---"
    } else {
        val v = name.split('-')
        s += v[1].takeLast(1)
        s += '-'
        s += v[2].takeLast(1)
    }
    s
}