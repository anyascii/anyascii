package com.anyascii.build

import com.anyascii.build.gen.generate
import com.ibm.icu.lang.UCharacter
import java.nio.file.Path
import java.time.Duration
import java.time.Instant
import java.util.Locale

fun main() {
    val start = Instant.now()
    Locale.setDefault(Locale.ROOT)

    val table = Table(ASCII)
            .then(decimalDigits())
            .then(emojis())
            .then(custom())
            .normalize(NFKC)
            .normalize(NFKD)
            .then(unihan())
            .normalize(NFKC)
            .cased(codePoints())
            .transliterate()
            .minus(ASCII.keys)

    table.write("table.tsv")

    generate(table)

    println(Duration.between(start, Instant.now()))
}

private fun decimalDigits() = codePoints("Nd").toTable { it.num }

private fun custom() = Table()
        .then(combiningDiacriticalMarks())
        .then(variationSelectors())
        .then(halfwidthFullwidth())
        .then(Table("spacing-modifier-letters"))
        .then(Table("currency-symbols"))
        .then(Table("letterlike-symbols"))
        .then(Table("general-punctuation"))
        .then(Table("nko"))
        .then(Table("math-symbols"))
        .then(Table("kanbun"))
        .then(tags())
        .then(cjkMisc())
        .then(Table("kangxi-radicals"))
        .then((0x31c0..0x31e3).toTable { it.name.substringAfterLast(' ') }) // cjk strokes
        .then(yi())
        .then(vai())
        .then(ethiopic())
        .then(dominoes())
        .then(Table("ocr"))
        .then(Table("ol-chiki"))
        .then(cyrillic())
        .then((0x24eb..0x24ff).toTable { it.num }) // circled numbers
        .then(coptic())
        .then(Table("yijing-hexagrams"))
        .then(Table("box-drawing"))
        .then((0x2580..0x259f).toTable { "#" }) // block elements
        .then(Table("control-pictures"))
        .then(Table("bopomofo"))
        .then(Table("hebrew"))
        .then(cypriot())
        .then(braille())
        .then(Table("gothic"))
        .then(Table("lydian"))
        .then(Table("lycian"))
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
        .then(arabic())
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
        .then(khmer())
        .then(Table("ogham"))
        .then(Table("thaana"))
        .then(Table("myanmar"))
        .then(Table("phags-pa"))
        .then(ideographicDescription())
        .then(Table("modifier-tone-letters"))
        .then(Table("indic-number-forms"))
        .then(Table("ancient-symbols"))
        .then(Table("carian"))
        .then(Table("tai-xuan-jing"))
        .then(Table("misc-symbols-and-arrows"))
        .then(Table("old-arabian"))
        .then(Table("supplemental-punctuation"))
        .then(tibetan())
        .then(Table("geometric-shapes"))
        .then(Table("math-operators"))
        .then(canadianSyllabics())
        .then(Table("meetei-mayek"))
        .then(Table("buginese"))
        .then(Table("alchemical"))
        .then(phoenician())
        .then(linearAB())
        .then(Table("chess-symbols"))
        .then(Table("ornamental-dingbats"))
        .then(countingRodNumerals())
        .then(mayanNumerals())
        .then(shorthandFormatControls())
        .then(Table("miao"))
        .then(Table("makasar"))
        .then(Table("pau-cin-hau"))
        .then(Table("elbasan"))
        .then(Table("caucasian-albanian"))
        .then(Table("tai-le"))
        .then(Table("mongolian"))
        .then(bamum())
        .then(Table("syloti-nagri"))
        .then(Table("kayah-li"))
        .then(Table("rejang"))
        .then(Table("cham"))
        .then(Table("tai-viet"))
        .then(Table("javanese"))
        .then(Table("batak"))
        .then(Table("sundanese"))
        .then(cherokee())
        .then(Table("new-tai-lue"))
        .then(Table("saurashtra"))
        .then(syriac())
        .then(Table("limbu"))
        .then(Table("symbols-for-legacy-computing"))
        .then(Table("mandaic"))
        .then(Table("specials"))
        .then(Table("lisu"))
        .then(Table("mahjong-tiles"))
        .then(Table("playing-cards"))
        .then(Table("samaritan"))
        .then(Table("tai-tham"))
        .then(nushu())
        .then(adlam())
        .then(Table("sora-sompeng"))
        .then(osage())
        .then(medefaidrin())
        .then(egyptianHieroglyphs())
        .then(marchen())
        .then(Table("ugaritic"))
        .then(Table("shavian"))
        .then(warangCiti())
        .then(Table("dives-akuru"))
        .then(copticEpact())
        .then(Table("old-permic"))
        .then(Table("avestan"))

private fun cyrillic() = Table("cyrillic")
        .cased(codePoints("Cyrl"))
        .aliasing((0xa674..0xa67b) + (0xa69e..0xa69f) + (0x2de0..0x2dff) - 0x2df5) { it.replace("COMBINING CYRILLIC", "CYRILLIC SMALL") }

private fun coptic() = Table("coptic")
        .cased(codePoints("Copt"))

private fun yi() = Table()
        .then(0xa015, "w")
        .then((0xa000..0xa48c).toTable { it.name.substringAfterLast(' ').lower() }) // syllables
        .then((0xa490..0xa4c6).toTable { it.name.substringAfterLast(' ') }) // radicals

private fun vai() = Table("vai")
        .then((0xa500..0xa62b).toTable { it.name.substringAfterLast(' ').lower() })

private fun dominoes() = (0x1f030..0x1f093).toTable {
    val name = it.name.removePrefix("DOMINO TILE ")
    if ("BACK" in name) return@toTable "-"
    return@toTable name.split('-').let { it[1].takeLast(1) + it[2].takeLast(1) }
}

private fun cypriot() = codePoints("Cprt").toTable { it.name.substringAfterLast(' ').lower() }

private fun braille() = Table("braille")
        .then(codePoints("Brai").toTable { "{${it.name.substringAfterLast('-')}}" })

private fun georgian() = Table("georgian")
        .aliasing(codePoints("Geor").filterName { "SMALL LETTER" in it }) { it.replace("SMALL ", "") }
        .transliterate()
        .cased(codePoints("Geor"))

private fun armenian() = Table("armenian")
        .cased(codePoints("Armn"))

private fun latin() = Table("latin")
        .cased(codePoints("Latn"))

private fun katakana() = Table("katakana")
        .aliasing(codePoints("Kana").filterName { it.startsWith("KATAKANA LETTER SMALL") }) { it.replace("SMALL ", "") }

private fun hiragana() = Table("hiragana")
        .aliasing(codePoints("Hira").filterName { it.startsWith("HIRAGANA LETTER SMALL") }) { it.replace("SMALL ", "") }
        .then(codePoints("Hira").filterName { it.startsWith("HENTAIGANA") }.toTable { it.name.substringAfterLast(' ').substringBefore('-').lower() })

private fun deseret() = Table("deseret")
        .cased(codePoints("Dsrt"))

private fun oldItalic() = Table("old-italic")
        .then((0x10320..0x10323).toTable { ROMAN_NUMERALS.getValue(it.numericValue) })

private fun sinhala() = Table("sinhala")
        .then((0x111e0..0x111ff).filterDefined().toTable { it.num })

private fun dingbats() = Table("dingbats")
        .then((0x2776..0x2793).toTable { it.num })

private fun cjkMisc() = Table("cjk-misc")
        .then((0x3021..0x3029).toTable { "${(it - 0x3021 + 1)}" }) // hangzhou numerals
        .then((0x3220..0x3229).toTable { "(${(it - 0x3220 + 1)})" }) // parenthesized numbers
        .then((0x3248..0x324f).toTable { it.num }) // circled number on black square
        .then((0x3280..0x3289).toTable { "(${(it - 0x3280 + 1)})" }) // circled numbers
        .then((0x32c0..0x32cb).toTable { "${(it - 0x32c0 + 1)}M" }) // telegraph months
        .then((0x3358..0x3370).toTable { "${(it - 0x3358)}H" }) // telegraph hours
        .then((0x33e0..0x33fe).toTable { "${(it - 0x33e0 + 1)}D" }) // telegraph days

private fun glagolitic() = Table("glagolitic")
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

private fun tibetan() = Table("tibetan")
        .aliasing(codePoints("Tibt").filterName { "SUBJOINED LETTER FIXED-FORM" in it }) { it.replace("SUBJOINED LETTER FIXED-FORM", "LETTER") }
        .aliasing(codePoints("Tibt").filterName { "SUBJOINED LETTER" in it && "FIXED-FORM" !in it }) { it.replace("SUBJOINED LETTER", "LETTER") }

private fun canadianSyllabics() = Table("canadian-syllabics")
        .then(codePoints("Cans").toTable { it.name.substringAfterLast(' ').lower() })

private fun halfwidthFullwidth() = (0xff00..0xffef).filterDefined().toTable { cp ->
    var name = cp.name.substringAfter(' ')
    if ("VOICED SOUND MARK" in name) name = name.replace("KATAKANA", "KATAKANA-HIRAGANA")
    else if (name.startsWith("FORMS ")) name = name.replace("FORMS", "BOX DRAWINGS")
    CodePoint(name).asString()
}

private fun phoenician() = Table("phoenician")
        .then((0x10916..0x1091b).toTable { it.num })
        .then((0x10858..0x1085f).toTable { it.num })
        .then((0x10879..0x1087f).toTable { it.num })
        .then((0x108fb..0x108ff).toTable { it.num })
        .then((0x108a7..0x108af).toTable { it.num })
        .then((0x10b58..0x10b5f).toTable { it.num })
        .then((0x10b78..0x10b7f).toTable { it.num })
        .then((0x10ba9..0x10baf).toTable { it.num })
        .then((0x10f1d..0x10f25).toTable { it.num })
        .then((0x10fc5..0x10fcb).toTable { it.num })
        .then((0x10f51..0x10f54).toTable { it.num })

private fun countingRodNumerals() = (0x1d360..0x1d378).toTable { NUMBER_SPELLOUT.parse(it.name.substringAfterLast(' ').lower()).toString() }

private fun mayanNumerals() = (0x1d2e0..0x1d2f3).toTable { it.num }

private fun shorthandFormatControls() = (0x1bca0..0x1bcaf).filterDefined().toTable { it.toString(16).takeLast(1) }

private fun bamum() = Table("bamum")
        .then((0xa6e6..0xa6ef).toTable { it.num })
        .then((0xa6a0..0xa6e5).toTable { it.name.substringAfterLast(' ').lower().capitalize() })

private fun cherokee() = codePoints("Cher").filter { UCharacter.isULowercase(it) }.toTable { it.name.substringAfterLast(' ').lower() }
        .cased(codePoints("Cher"))

private fun syriac() = Table("syriac")
        .aliasing((0x0860..0x086a)) { it.replace("SYRIAC LETTER MALAYALAM", "MALAYALAM LETTER") }

private fun nushu() = Table().apply {
    forEachLine(Path.of("input/NushuSources.txt")) { line ->
        if (line.isEmpty() || line.startsWith('#')) return@forEachLine
        val split = line.split('\t', limit = 3)
        if (split[1] != "kReading") return@forEachLine
        val cp = split[0].drop(2).toInt(16)
        this[cp] = split[2]
    }
}

private fun adlam() = Table("adlam")
        .cased(codePoints("Adlm"))

private fun combiningDiacriticalMarks() = Table()
        .then((0x300..0x362).toTable { "" }) // Combining Diacritical Marks
        .then((0x363..0x36f).toTable { it.name.substringAfterLast(' ').lower() })
        .then((0x1ab0..0x1aff).filterDefined().toTable { "" }) // Combining Diacritical Marks Extended
        .then((0x1dc0..0x1dff).filterDefined().toTable { "" }) // Combining Diacritical Marks Supplement
        .then((0x20d0..0x20ff).filterDefined().toTable { "" }) // Combining Diacritical Marks for Symbols
        .then((0xfe20..0xfe2f).toTable { "" }) // Combining Half Marks

private fun variationSelectors() = Table()
        .then((0xfe00..0xfe0f).toTable { "" }) // Variation Selectors
        .then((0xe0100..0xe01ef).toTable { "" }) // Variation Selectors Supplement

private fun tags() = Table()
        .then((0xe0020..0xe007e).toTable { (it - 0xe0000).asString() })
        .then((0xe0000..0xe007f).filterDefined().toTable { "" })

private fun osage() = Table("osage")
        .cased(codePoints("Osge"))

private fun medefaidrin() = Table("medefaidrin")
        .cased(codePoints("Medf"))
        .then((0x16e80..0x16e96).toTable { it.num })

private fun khmer() = Table("khmer")
        .then((0x17f0..0x17f9).toTable { it.num })

private fun marchen() = Table("marchen")
        .then(codePoints("Marc").toTable { CodePoint(it.name.replace("MARCHEN", "TIBETAN")).let { if (it == -1) "" else it.asString() } })

private fun warangCiti() = Table("warang-citi")
        .cased(codePoints("Wara"))
        .then((0x118ea..0x118f2).toTable { it.num })

private fun copticEpact() = Table()
        .then(0x102e0, "k")
        .then((0x102e1..0x102fb).toTable { it.num })
