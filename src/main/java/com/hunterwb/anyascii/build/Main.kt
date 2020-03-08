package com.hunterwb.anyascii.build

import com.hunterwb.anyascii.build.gen.generate
import com.ibm.icu.text.Transliterator
import java.util.Locale

fun main() {
    Locale.setDefault(Locale.ROOT)
    val table = ascii()
            .then(decimalDigits())
            .then(emojis())
            .then(custom())
            .normalize(NFKC)
            .then(icu("::Latin-ASCII; [:^ASCII:]>;"))
            .then(unihan())
            .then(ccCedict())
            .normalize(NFKC)
            .then(Table("unidecode"))
            .then(icu("::Any-Latin; ::Latin-ASCII; [:^ASCII:]>;"))
            .normalize(NFKC)
            .cased()
            .minus(ascii().keys)
            .write("table.tsv")

    table.then(ascii())
    generate(table)
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

private fun ascii(): Table = (0..127).toTable { toString(it) }

private fun decimalDigits() = codePoints("Nd").toTable { numericValue(it).toString() }

private fun custom() = Table()
        .then(Table("currency-symbols"))
        .then(Table("letterlike-symbols"))
        .then(Table("general-punctuation"))
        .then(Table("nko"))
        .then(Table("math-symbols-a"))
        .then(Table("math-symbols-b"))
        .then(Table("kanbun"))
        .then((0xe0020..0xe007e).toTable { toString(it - 0xe0000) }) // tags
        .then(Table("han-misc"))
        .then(Table("kangxi-radicals"))
        .then(Table("cjk-radicals"))
        .then((0x31c0..0x31e3).toTable { name(it).substringAfterLast(' ') }) // cjk strokes
        .then((0x33e0..0x33fe).toTable { "${(it - 0x33e0 + 1)}D" }) // telegraph days
        .then((0x3358..0x3370).toTable { "${(it - 0x3358)}H" }) // telegraph hours
        .then((0x32c0..0x32cb).toTable { "${(it - 0x32c0 + 1)}M" }) // telegraph months
        .then((0x3220..0x3229).toTable { "(${(it - 0x3220 + 1)})" }) // parenthesized numbers
        .then((0x3280..0x3289).toTable { "(${(it - 0x3280 + 1)})" }) // circled numbers
        .then((0x3021..0x3029).toTable { "${(it - 0x3021 + 1)}" }) // hangzhou numerals
        .then((0x3248..0x324f).toTable { numericValue(it).toString() }) // circled number on black square
        .then(yi())
        .then(vai())
        .then(ethiopic())
        .then(dominoes())
        .then(Table("ocr"))
        .then(olChiki())
        .then(cyrillic())
        .then((0x24eb..0x24ff).toTable { numericValue(it).toString() }) // circled numbers
        .then(greek())
        .then(coptic())
        .then(Table("hexagrams"))
        .then(Table("box-drawing"))
        .then((0x2580..0x259f).toTable { "#" }) // block elements
        .then(Table("control-pictures"))
        .then(bopomofo())
        .then(Table("hebrew").normalize(NFKC))
        .then(cypriot())
        .then(braille())
        .then(Table("gothic"))
        .then(lydian())
        .then(lycian())
        .then(georgian())
        .then(armenian())
        .then(Table("thai"))
        .then(Table("number-forms"))
        .then(Table("latin").cased().normalize(NFKD))
        .then(Table("squared-katakana-words"))
        .then(katakana())
        .then(hiragana())
        .then(Table("lao"))
        .then(Table("runic"))
        .then(oldItalic())
        .then(Table("osmanya"))
        .then(Table("deseret").cased())
        .then(Table("arabic").normalize(NFKC))
        .then(Table("oriya"))
        .then(Table("bengali"))
        .then(Table("devanagari"))
        .then(Table("gurmukhi"))
        .then(Table("gujarati"))
        .then(Table("tamil"))
        .then(Table("telugu"))
        .then(Table("kannada"))
        .then(Table("malayalam"))
        .then(sinhala())
        .then(hangul())
        .then(Table("misc-symbols"))
        .then(Table("arrows"))
        .then(Table("misc-technical"))
        .then(dingbats())

private fun cyrillic() = Table()
        .then(Table("cyrillic"))
        .cased()
        .normalize(NFKC)
        .aliasing((0xa674..0xa67b) + (0xa69e..0xa69f) + (0x2de0..0x2dff) - 0x2df5) { it.replace("COMBINING CYRILLIC", "CYRILLIC SMALL") }

private fun greek() = Table()
        .then(Table("greek-symbols"))
        .then(greekMath())
        .then(Table("greek"))
        .cased()
        .minus(0x345)
        .apply {
            then(codePoints("Grek").filter { name(it).contains("WITH DASIA") }.toTable {
                val n = name(it).substringBefore(" WITH")
                val o = getValue(codePoint(n))
                if ("RHO" in n) {
                    "${o}h"
                } else {
                    if ("CAPITAL" in n) "H${lower(o)}" else "h$o"
                }
            })
        }
        .normalize(NFKD, "")
        .aliasing((0x1d26..0x1d2a) + 0xab65) { it.replace("LETTER SMALL CAPITAL", "CAPITAL LETTER") }

private fun greekMath() = Table()
        .then(Table("greek-math"))
        .cased()
        .normalize(NFKC)
        .retain((0x1d6a8..0x1d7cb) + 0x2207 + 0x2202 + 0x3f4 + 0x3f5 + 0x3d1 + 0x3f0 + 0x3d5 + 0x3f1 + 0x3d6 + 0x3d0)

private fun coptic() = Table()
        .then(Table("coptic"))
        .cased()

private fun yi() = Table()
        .then(0xa015, "w")
        .then((0xa000..0xa48c).toTable { name(it).substringAfterLast(' ').toLowerCase() }) // syllables
        .then((0xa490..0xa4c6).toTable { name(it).substringAfterLast(' ') }) // radicals

private fun vai() = Table()
        .then(Table("vai"))
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

private fun bopomofo() = codePoints("Bopo").toTable { cp ->
    val name = name(cp)
    if ("TONE" in name) return@toTable ""
    name.substringAfter("LETTER ").substringBefore(' ').toLowerCase().capitalize()
}

private fun cypriot() = codePoints("Cprt").toTable { name(it).substringAfterLast(' ').toLowerCase() }

private fun braille() = Table()
        .then(Table("braille"))
        .then(codePoints("Brai").toTable { "{${name(it).substringAfterLast('-')}}" })

private fun lydian() = codePoints("Lydi").toTable {
    val name = name(it).toLowerCase()
    if ("letter" in name) name.substringAfterLast(' ') else ""
}

private fun lycian() = codePoints("Lyci").toTable { name(it).toLowerCase().substringAfterLast(' ') }

private fun georgian() = Table()
        .then(Table("georgian"))
        .aliasing(codePoints("Geor").filter { "SMALL LETTER" in name(it) }) { it.replace("SMALL ", "") }
        .normalize(NFKD)
        .cased()

private fun armenian() = Table()
        .then(Table("armenian"))
        .normalize(NFKD)
        .cased()

private fun katakana() = Table()
        .then(Table("katakana"))
        .aliasing(codePoints("Kana").filter { name(it).startsWith("KATAKANA LETTER SMALL") }) { it.replace("SMALL ", "") }
        .normalize(NFKC)

private fun hiragana() = Table()
        .then(Table("hiragana"))
        .aliasing(codePoints("Hira").filter { name(it).startsWith("HIRAGANA LETTER SMALL") }) { it.replace("SMALL ", "") }
        .normalize(NFKC)

private fun oldItalic() = Table()
        .then((0x10320..0x10323).toTable { ROMAN_NUMERALS.getValue(numericValue(it)) })
        .then((0x10300..0x1032f).filter { isDefined(it) }.toTable {
            val n = name(it).substringAfterLast(' ').toLowerCase()
            if (n.toSet().size == 1) return@toTable n
            return@toTable n.replace("[aeu]".toRegex(), "")
        })

private fun sinhala() = Table()
        .then(Table("sinhala"))
        .then((0x111e0..0x111ff).filter { isDefined(it) }.toTable { numericValue(it).toString() })

private fun hangul() = Table()
        .then(icu("[:^Hang:]>; ::Any-Latin; ::Latin-ASCII; [:^ASCII:]>;"))

private fun dingbats() = Table()
        .then(Table("dingbats"))
        .then((0x2776..0x2793).toTable { numericValue(it).toString() })