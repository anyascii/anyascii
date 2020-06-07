package com.hunterwb.anyascii.build

import com.hunterwb.anyascii.build.gen.generate
import com.ibm.icu.text.Transliterator
import java.time.Duration
import java.time.Instant
import java.util.Locale

fun main() {
    val start = Instant.now()
    Locale.setDefault(Locale.ROOT)

    val table = ascii()
            .then(decimalDigits())
            .then(emojis())
            .then(custom())
            .normalize(NFKC)
            .normalize(NFKD)
            .then(unihan())
            .normalize(NFKC)
            .then(Table("unidecode"))
            .then(icu("::Any-Latin; ::Latin-ASCII; [:^ASCII:]>;"))
            .normalize(NFKC)
            .cased(codePoints())
            .transliterate()
            .minus(ascii().keys)
            .write("table.tsv")

    table.then(ascii())
    generate(table)

    println(Duration.between(start, Instant.now()))
}

private fun icu(rules: String): Table {
    val table = Table()
    val transliterator = Transliterator.createFromRules(rules, rules, Transliterator.FORWARD)
    for (cp in 128..Character.MAX_CODE_POINT) {
        val output = transliterator.transliterate(cp.asString())
        if (output.isNotEmpty()) {
            table[cp] = output
        }
    }
    return table
}

private fun ascii(): Table = (0..127).toTable { it.asString() }

private fun decimalDigits() = codePoints("Nd").toTable { it.numericValue.toString() }

private fun custom() = Table()
        .then(combiningDiacriticalMarks())
        .then(halfwidthFullwidth())
        .then(Table("spacing-modifier-letters"))
        .then(Table("currency-symbols"))
        .then(Table("letterlike-symbols"))
        .then(Table("general-punctuation"))
        .then(Table("nko"))
        .then(Table("math-symbols"))
        .then(Table("kanbun"))
        .then((0xe0020..0xe007e).toTable { (it - 0xe0000).asString() }) // tags
        .then(cjkMisc())
        .then(Table("kangxi-radicals"))
        .then(Table("cjk-radicals"))
        .then((0x31c0..0x31e3).toTable { it.name.substringAfterLast(' ') }) // cjk strokes
        .then(yi())
        .then(vai())
        .then(ethiopic())
        .then(dominoes())
        .then(Table("ocr"))
        .then(olChiki())
        .then(cyrillic())
        .then((0x24eb..0x24ff).toTable { it.numericValue.toString() }) // circled numbers
        .then(coptic())
        .then(Table("hexagrams"))
        .then(Table("box-drawing"))
        .then((0x2580..0x259f).toTable { "#" }) // block elements
        .then(Table("control-pictures"))
        .then(Table("bopomofo"))
        .then(Table("hebrew"))
        .then(cypriot())
        .then(braille())
        .then(Table("gothic"))
        .then(lydian())
        .then(lycian())
        .then(georgian())
        .then(armenian())
        .then(Table("thai"))
        .then(Table("number-forms"))
        .then(Table("latin-common"))
        .then(latin())
        .then(greek())
        .then(katakana())
        .then(hiragana())
        .then(Table("lao"))
        .then(Table("runic"))
        .then(oldItalic())
        .then(Table("osmanya"))
        .then(deseret())
        .then(Table("arabic"))
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
        .then(Table("tifinagh"))
        .then(glagolitic())
        .then(baybayin())
        .then(Table("khmer"))
        .then(Table("ogham"))
        .then(Table("thaana"))
        .then(Table("myanmar"))
        .then(Table("phags-pa"))
        .then(ideographicDescription())
        .then(Table("modifier-tone-letters"))
        .then(Table("indic-number-forms"))
        .then(Table("ancient-symbols"))
        .then(carian())
        .then(Table("tai-xuan-jing"))
        .then(Table("misc-symbols-and-arrows"))
        .then(Table("old-south-arabian"))
        .then(Table("supplemental-punctuation"))
        .then(tibetan())
        .then(Table("geometric-shapes"))
        .then(Table("math-operators"))
        .then(canadianSyllabics())
        .then(Table("meetei-mayek"))
        .then(Table("buginese"))
        .then(Table("alchemical"))
        .then(phoenician())
        .then(linearAb())
        .then(imperialAramaic())
        .then(Table("chess-symbols"))
        .then(Table("ornamental-dingbats"))
        .then(countingRodNumerals())
        .then(mayanNumerals())
        .then(shorthandFormatControls())
        .then(Table("miao"))

private fun cyrillic() = Table()
        .then(Table("cyrillic"))
        .cased(codePoints("Cyrl"))
        .aliasing((0xa674..0xa67b) + (0xa69e..0xa69f) + (0x2de0..0x2dff) - 0x2df5) { it.replace("COMBINING CYRILLIC", "CYRILLIC SMALL") }

private fun coptic() = Table()
        .then(Table("coptic"))
        .cased(codePoints("Copt"))

private fun yi() = Table()
        .then(0xa015, "w")
        .then((0xa000..0xa48c).toTable { it.name.substringAfterLast(' ').lower() }) // syllables
        .then((0xa490..0xa4c6).toTable { it.name.substringAfterLast(' ') }) // radicals

private fun vai() = Table()
        .then(Table("vai"))
        .then((0xa500..0xa62b).toTable { it.name.substringAfterLast(' ').lower() })

private fun ethiopic() = Table()
        .then(codePoints("Ethi").filterName { it.contains("SYLLABLE") }.toTable {
            val name = it.name.removePrefix("ETHIOPIC SYLLABLE ").removePrefix("SEBATBEIT ").lower()
            if (' ' in name) "'${name.substringAfterLast(' ')}" else name
        })

private fun olChiki() = Table()
        .then((0x1c5a..0x1c77).toTable {
            val name = it.name.substringAfterLast(' ').lower()
            if (name.startsWith('l')) name.substring(1) else name.replace("[aeiou]".toRegex(), "")
        })

private fun dominoes() = (0x1f030..0x1f093).toTable {
    val name = it.name.removePrefix("DOMINO TILE ")
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

private fun cypriot() = codePoints("Cprt").toTable { it.name.substringAfterLast(' ').lower() }

private fun braille() = Table()
        .then(Table("braille"))
        .then(codePoints("Brai").toTable { "{${it.name.substringAfterLast('-')}}" })

private fun lydian() = codePoints("Lydi").toTable {
    val name = it.name.lower()
    if ("letter" in name) name.substringAfterLast(' ') else ""
}

private fun lycian() = codePoints("Lyci").toTable { it.name.lower().substringAfterLast(' ') }

private fun georgian() = Table()
        .then(Table("georgian"))
        .aliasing(codePoints("Geor").filterName { "SMALL LETTER" in it }) { it.replace("SMALL ", "") }
        .cased(codePoints("Geor"))

private fun armenian() = Table()
        .then(Table("armenian"))
        .cased(codePoints("Armn"))

private fun latin() = Table()
        .then(Table("latin"))
        .cased(codePoints("Latn"))

private fun katakana() = Table()
        .then(Table("katakana"))
        .aliasing(codePoints("Kana").filterName { it.startsWith("KATAKANA LETTER SMALL") }) { it.replace("SMALL ", "") }

private fun hiragana() = Table()
        .then(Table("hiragana"))
        .aliasing(codePoints("Hira").filterName { it.startsWith("HIRAGANA LETTER SMALL") }) { it.replace("SMALL ", "") }
        .then(codePoints("Hira").filterName { it.startsWith("HENTAIGANA") }.toTable { it.name.substringAfterLast(' ').substringBefore('-').lower() })

private fun deseret() = Table()
        .then(Table("deseret"))
        .cased(codePoints("Dsrt"))

private fun oldItalic() = Table()
        .then((0x10320..0x10323).toTable { ROMAN_NUMERALS.getValue(it.numericValue) })
        .then((0x10300..0x1032f).filterDefined().toTable {
            val n = it.name.substringAfterLast(' ').lower()
            if (n.toSet().size == 1) return@toTable n
            return@toTable n.replace("[aeu]".toRegex(), "")
        })

private fun sinhala() = Table()
        .then(Table("sinhala"))
        .then((0x111e0..0x111ff).filterDefined().toTable { it.numericValue.toString() })

private fun dingbats() = Table()
        .then(Table("dingbats"))
        .then((0x2776..0x2793).toTable { it.numericValue.toString() })

private fun cjkMisc() = Table()
        .then(Table("cjk-misc"))
        .then((0x3021..0x3029).toTable { "${(it - 0x3021 + 1)}" }) // hangzhou numerals
        .then((0x3220..0x3229).toTable { "(${(it - 0x3220 + 1)})" }) // parenthesized numbers
        .then((0x3248..0x324f).toTable { it.numericValue.toString() }) // circled number on black square
        .then((0x3280..0x3289).toTable { "(${(it - 0x3280 + 1)})" }) // circled numbers
        .then((0x32c0..0x32cb).toTable { "${(it - 0x32c0 + 1)}M" }) // telegraph months
        .then((0x3358..0x3370).toTable { "${(it - 0x3358)}H" }) // telegraph hours
        .then((0x33e0..0x33fe).toTable { "${(it - 0x33e0 + 1)}D" }) // telegraph days

private fun glagolitic() = Table()
        .then(Table("glagolitic"))
        .cased(codePoints("Glag"))

private fun baybayin() = (0x1700..0x177f).filterDefined().toTable { cp ->
    // tagalog, hanunoo, buhid, tagbanwa
    val name = cp.name.lower()
    val last = name.substringAfterLast(' ')
    when {
        "virama" in name || "pamudpod" in name -> ""
        "single punctuation" in name -> "|"
        "double punctuation" in name -> "||"
        last.length == 1 -> last
        else -> last.dropLast(1)
    }
}

private fun ideographicDescription() = (0x2ff0..0x2fff).filterDefined().toTable { it.toString(16).takeLast(1) }

// https://www.unicode.org/wg2/docs/n3020.pdf
private fun carian() = (0x102a0..0x102df).filterDefined().toTable { it.name.substringAfterLast(' ') }

private fun tibetan() = Table()
        .then(Table("tibetan"))
        .aliasing(codePoints("Tibt").filterName { "SUBJOINED LETTER FIXED-FORM" in it }) { it.replace("SUBJOINED LETTER FIXED-FORM", "LETTER") }
        .aliasing(codePoints("Tibt").filterName { "SUBJOINED LETTER" in it && "FIXED-FORM" !in it }) { it.replace("SUBJOINED LETTER", "LETTER") }

private fun canadianSyllabics() = Table()
        .then(Table("canadian-syllabics"))
        .then(codePoints("Cans").toTable { it.name.substringAfterLast(' ').lower() })

private fun halfwidthFullwidth() = (0xff00..0xffef).filterDefined().toTable { cp ->
    var name = cp.name.substringAfter(' ')
    if ("VOICED SOUND MARK" in name) name = name.replace("KATAKANA", "KATAKANA-HIRAGANA")
    else if (name.startsWith("FORMS ")) name = name.replace("FORMS", "BOX DRAWINGS")
    CodePoint(name).asString()
}

private fun combiningDiacriticalMarks() = Table()
        .then((0x363..0x36f).toTable { it.name.substringAfterLast(' ').lower() })
        .then((0x300..0x362).toTable { "" })

private fun phoenician() = Table()
        .then(Table("phoenician"))
        .then((0x10916..0x1091b).toTable { it.numericValue.toString() })

private val LINEAR_AB_ID = "(\\S*?)(\\d{3})(\\S*)".toRegex()

// http://www.people.ku.edu/~jyounger/LinearA/LinAIdeograms/
private fun linearAb() = codePoints("Lina").plus(codePoints("Linb")).toTable { cp ->
    val (pre, id, post) = checkNotNull(LINEAR_AB_ID.find(cp.name)).destructured
    val post2 = post.lower().replace("-102", "/102").removeSuffix("-vas")
    "$pre$id$post2"
}

private fun imperialAramaic() = Table()
        .then(Table("imperial-aramaic"))
        .then((0x10858..0x1085f).toTable { it.numericValue.toString() })

private fun countingRodNumerals() = (0x1d360..0x1d378).toTable { NUMBER_SPELLOUT.parse(it.name.substringAfterLast(' ').lower()).toString() }

private fun mayanNumerals() = (0x1d2e0..0x1d2f3).toTable { it.numericValue.toString() }

private fun shorthandFormatControls() = (0x1bca0..0x1bcaf).filterDefined().toTable { it.toString(16).takeLast(1) }