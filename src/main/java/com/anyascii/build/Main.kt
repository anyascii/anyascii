package com.anyascii.build

import com.anyascii.build.gen.generate
import com.ibm.icu.lang.UCharacter.*
import com.ibm.icu.lang.UCharacter.UnicodeBlock.*
import com.ibm.icu.lang.UScript
import com.ibm.icu.util.ULocale
import java.nio.file.Path
import kotlin.io.path.forEachLine

fun main() {
    ULocale.setDefault(ULocale.ROOT)

    val table = ASCII.toTable { String(it) }
            .normalize(NFKD)
            .then(emojis())
            .then(custom())
            .normalize(NFKC)
            .normalize(NFKD)
            .then(unihan())
            .normalize(NFKC)
            .cased(ALL)
            .then(ALL.intValues())
            .transliterate()

    printCoverage(table)

    generate(table)

    table.remove(ASCII).write("table.tsv")
}

private fun custom() = Table()
        .then(category(CONTROL).toTable { "" })
        .then(Table("combining-diacritical-marks"))
        .then(variationSelectors())
        .then(halfwidthFullwidth())
        .then(Table("spacing-modifier-letters"))
        .then(Table("currency-symbols"))
        .then(Table("letterlike-symbols"))
        .then(Table("general-punctuation"))
        .then(Table("nko"))
        .then(Table("miscellaneous-mathematical-symbols-a"))
        .then(Table("miscellaneous-mathematical-symbols-b"))
        .then(Table("kanbun"))
        .then(tags())
        .then(Table("kangxi-radicals"))
        .then(block(CJK_STROKES).toTable { it.name.substringAfterLast(' ') })
        .then(yi())
        .then(vai())
        .then(ethiopic())
        .then(dominoes())
        .then(Table("optical-character-recognition"))
        .then(Table("ol-chiki"))
        .then(cyrillic())
        .then(Table("coptic"))
        .then(Table("yijing"))
        .then(Table("box-drawing"))
        .then(block(BLOCK_ELEMENTS).toTable { "#" })
        .then(Table("control-pictures"))
        .then(Table("bopomofo"))
        .then(hebrew())
        .then(cypriot())
        .then(braille())
        .then(Table("gothic"))
        .then(Table("lydian"))
        .then(Table("lycian"))
        .then(georgian())
        .then(Table("armenian"))
        .then(Table("thai"))
        .then(Table("number-forms"))
        .then(latin())
        .then(greek())
        .then(kana())
        .then(Table("lao"))
        .then(Table("runic"))
        .then(Table("old-italic"))
        .then(Table("osmanya"))
        .then(Table("deseret"))
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
        .then(Table("sinhala"))
        .then(hangul())
        .then(Table("miscellaneous-symbols"))
        .then(Table("arrows"))
        .then(Table("miscellaneous-technical"))
        .then(Table("dingbats"))
        .then(Table("tifinagh"))
        .then(glagolitic())
        .then(Table("philippine"))
        .then(Table("khmer"))
        .then(Table("ogham"))
        .then(Table("thaana"))
        .then(Table("myanmar"))
        .then(Table("phags-pa"))
        .then(ideographicDescription())
        .then(Table("modifier-tone-letters"))
        .then(Table("common-indic-number-forms"))
        .then(Table("ancient-symbols"))
        .then(Table("carian"))
        .then(Table("tai-xuan-jing"))
        .then(Table("miscellaneous-symbols-and-arrows"))
        .then(Table("old-arabian"))
        .then(Table("supplemental-punctuation"))
        .then(Table("tibetan"))
        .then(Table("geometric-shapes"))
        .then(Table("geometric-shapes-extended"))
        .then(Table("mathematical-operators"))
        .then(canadianSyllabics())
        .then(Table("meetei-mayek"))
        .then(Table("buginese"))
        .then(Table("alchemical-symbols"))
        .then(Table("phoenician"))
        .then(linearAB())
        .then(Table("chess-symbols"))
        .then(Table("ornamental-dingbats"))
        .then(Table("counting-rod-numerals"))
        .then(block(SHORTHAND_FORMAT_CONTROLS).toTable { "" })
        .then(Table("miao"))
        .then(Table("makasar"))
        .then(Table("pau-cin-hau"))
        .then(Table("elbasan"))
        .then(Table("caucasian-albanian"))
        .then(Table("tai-le"))
        .then(Table("mongolian"))
        .then(Table("bamum"))
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
        .then(Table("syriac"))
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
        .then(Table("adlam"))
        .then(Table("sora-sompeng"))
        .then(Table("osage"))
        .then(Table("medefaidrin"))
        .then(egyptianHieroglyphs())
        .then(Table("marchen"))
        .then(Table("ugaritic"))
        .then(Table("shavian"))
        .then(Table("warang-citi"))
        .then(Table("dives-akuru"))
        .then(Table("old-permic"))
        .then(Table("avestan"))
        .then(Table("kharoshthi"))
        .then(Table("multani"))
        .then(Table("mahajani"))
        .then(Table("sharada"))
        .then(Table("balinese"))
        .then(Table("lepcha"))
        .then(Table("old-persian"))
        .then(Table("meroitic"))
        .then(Table("tirhuta"))
        .then(Table("modi"))
        .then(takri())
        .then(dogra())
        .then(khudawadi())
        .then(nandinagari())
        .then(Table("gunjala-gondi"))
        .then(Table("brahmi"))
        .then(Table("pahawh-hmong"))
        .then(Table("vedic-extensions"))
        .then(Table("manichaean"))
        .then(Table("ottoman-siyaq-numbers"))
        .then(Table("indic-siyaq-numbers"))
        .then(mendeKikakui())
        .then(Table("wancho"))
        .then(Table("ideographic-symbols-and-punctuation"))
        .then(phaistosDisc())
        .then(Table("old-turkic"))
        .then(Table("ahom"))
        .then(Table("zanabazar-square"))
        .then(Table("bassa-vah"))
        .then(Table("yezidi"))
        .then(Table("old-hungarian"))
        .then(enclosedAlphanumericSupplement())
        .then(Table("supplemental-symbols-and-pictographs"))
        .then(Table("transport-and-map-symbols"))
        .then(Table("miscellaneous-symbols-and-pictographs"))
        .then(anatolianHieroglyphs())
        .then(Table("hanifi-rohingya"))
        .then(Table("mro"))
        .then(Table("khojki"))
        .then(Table("chakma"))
        .then(Table("masaram-gondi"))
        .then(Table("kaithi"))
        .then(Table("bhaiksuki"))
        .then(Table("siddham"))
        .then(Table("soyombo"))
        .then(Table("newa"))
        .then(Table("grantha"))
        .then(Table("aegean-numbers"))
        .then(Table("nyiakeng-puachue-hmong"))
        .then(Table("ancient-greek-numbers"))
        .then(Table("cuneiform-numbers-and-punctuation"))
        .then(ancientGreekMusicalNotation())
        .then(Table("duployan"))
        .then(Table("khitan-small-script"))
        .then(tangut())
        .then(Table("musical-symbols"))
        .then(block(BYZANTINE_MUSICAL_SYMBOLS).toTable { "-" })
        .then(cjkSymbolsAndPunctuation())
        .then(enclosedCjkLettersAndMonths())
        .then(cjkCompatibility())
        .then(Table("cjk-compatibility-forms"))
        .then(enclosedIdeographicSupplement())
        .then(Table("old-uyghur"))
        .then(Table("vithkuqi"))
        .then(Table("toto"))
        .then(tangsa())
        .then(znamennyMusicalNotation())
        .then(cyproMinoan())

private fun cyrillic() = Table("cyrillic")
        .cased(script(UScript.CYRILLIC))
        .then(script(UScript.CYRILLIC).filter { "COMBINING CYRILLIC LETTER" in it.name }.alias { it.replace("COMBINING CYRILLIC", "CYRILLIC SMALL") })

private fun yi() = Table()
        .then(0xa015, "w")
        .then(block(YI_SYLLABLES).toTable { it.name.substringAfterLast(' ').lower() })
        .then(block(YI_RADICALS).toTable { it.name.substringAfterLast(' ') })

private fun dominoes() = (0x1f030..0x1f093).toTable {
    val name = it.name.removePrefix("DOMINO TILE ")
    if ("BACK" in name) return@toTable "-"
    return@toTable name.split('-').let { it[1].takeLast(1) + it[2].takeLast(1) }
}

private fun cypriot() = block(CYPRIOT_SYLLABARY).toTable { it.name.substringAfterLast(' ').lower() }

private fun braille() = Table("braille")
        .then(block(BRAILLE_PATTERNS).toTable { "{${it.name.substringAfterLast('-')}}" })

private fun georgian() = Table("georgian")
        .then(script(UScript.GEORGIAN).filter { "SMALL LETTER" in it.name }.alias { it.remove("SMALL ") })
        .transliterate()

private fun latin() = Table("latin")
        .cased(script(UScript.LATIN))

private fun kana() = Table("kana")
        .then(block(KANA_EXTENDED_B).toTable { "" })
        .then(script(UScript.HIRAGANA).filter { it.nameAlias.startsWith("HENTAIGANA") }.toTable { it.nameAlias.substringAfterLast(' ').substringBefore('-').lower() })
        .then(script(UScript.HIRAGANA).filter { it.name.startsWith("HIRAGANA LETTER") }.alias { it.replace("HIRAGANA", "KATAKANA") })
        .then(script(UScript.KATAKANA).filter { it.name.startsWith("KATAKANA LETTER SMALL") }.alias { it.remove("SMALL ") })

private fun glagolitic() = Table("glagolitic")
        .then(script(UScript.GLAGOLITIC).and(category(NON_SPACING_MARK)).alias { it.replace("COMBINING GLAGOLITIC LETTER", "GLAGOLITIC SMALL LETTER") })

private fun ideographicDescription() = block(IDEOGRAPHIC_DESCRIPTION_CHARACTERS).toTable { "+" }

private fun canadianSyllabics() = Table("canadian-syllabics")
        .then(script(UScript.CANADIAN_ABORIGINAL).toTable { it.name.substringAfterLast(' ').lower() })

private fun halfwidthFullwidth() = block(HALFWIDTH_AND_FULLWIDTH_FORMS).alias {
    var name = it.substringAfter(' ')
    if ("VOICED SOUND MARK" in name) name = name.replace("KATAKANA", "KATAKANA-HIRAGANA")
    else if (name.startsWith("FORMS ")) name = name.replace("FORMS", "BOX DRAWINGS")
    name
}

private fun cherokee() = script(UScript.CHEROKEE).and(category(LOWERCASE_LETTER)).toTable { it.name.substringAfterLast(' ').lower() }

private fun nushu() = Table().apply {
    Path.of("input/NushuSources.txt").forEachLine { line ->
        if (line.isEmpty() || line.startsWith('#')) return@forEachLine
        val split = line.split('\t', limit = 3)
        if (split[1] != "kReading") return@forEachLine
        val cp = parseUCodePoint(split[0])
        this[cp] = split[2].filter(ASCII_LETTERS)
    }
}

private fun variationSelectors() = Table()
        .then(block(VARIATION_SELECTORS).toTable { "" })
        .then(block(VARIATION_SELECTORS_SUPPLEMENT).toTable { "" })

private fun tags() = Table()
        .then((0xe0020..0xe007e).toTable { String(it - 0xe0000) })
        .then(block(TAGS).toTable { "" })

private fun hebrew() = Table("hebrew")
        .then((0x591..0x5af).toTable { "" })

private fun takri() = block(TAKRI).alias { it.remove("ARCHAIC ").replace("TAKRI", "DEVANAGARI") }

private fun dogra() = block(DOGRA).alias { it.replace("DOGRA", "DEVANAGARI") }

private fun khudawadi() = block(KHUDAWADI).alias { it.replace("KHUDAWADI", "DEVANAGARI") }

private fun nandinagari() = block(NANDINAGARI).alias { it.replace("NANDINAGARI", "DEVANAGARI") }

private fun mendeKikakui() = block(MENDE_KIKAKUI).and(category(OTHER_LETTER)).toTable { it.name.substringAfterLast(' ').lower().replace("ee", "e").replace("oo", "o") }
        .then((0x1e8d0..0x1e8d6).toTable { "" })

private fun phaistosDisc() = Table()
        .then(0x101fd, ",")
        .then(block(PHAISTOS_DISC).toTable { String.format("%02d", it - 0x101d0 + 1) })

private fun enclosedAlphanumericSupplement() = Table("enclosed-alphanumeric-supplement")
        .then((0x1f150..0x1f169).toTable { it.name.substringAfterLast(' ') })
        .then((0x1f170..0x1f1ac).toTable { it.name.substringAfterLast(' ') })
        .then((0x1f1e6..0x1f1ff).toTable { it.name.substringAfterLast(' ') })

private val ANATOLIAN_CATALOGUE_ID = "A(\\d{3}[A-Z]?)".toRegex()

private fun anatolianHieroglyphs() = block(ANATOLIAN_HIEROGLYPHS).toTable {
    ANATOLIAN_CATALOGUE_ID.findOnly(it.name).groupValues[1].stripLeading('0').lower()
}

private fun ancientGreekMusicalNotation() = Table("ancient-greek-musical-notation")
        .then(block(ANCIENT_GREEK_MUSICAL_NOTATION).toTable { it.name.substringAfterLast('-') })

private fun cjkSymbolsAndPunctuation() = Table("cjk-symbols-and-punctuation")
        .then(block(CJK_SYMBOLS_AND_PUNCTUATION).intValues())

private fun enclosedCjkLettersAndMonths() = Table("enclosed-cjk-letters-and-months")
        .then((0x3220..0x3229).toTable { "(${(it - 0x3220 + 1)})" }) // parenthesized numbers
        .then((0x3280..0x3289).toTable { "(${(it - 0x3280 + 1)})" }) // circled numbers
        .then((0x32c0..0x32cb).toTable { "${(it - 0x32c0 + 1)}M" }) // telegraph months

private fun cjkCompatibility() = Table("cjk-compatibility")
        .then((0x3358..0x3370).toTable { "${(it - 0x3358)}H" }) // telegraph hours
        .then((0x33e0..0x33fe).toTable { "${(it - 0x33e0 + 1)}D" }) // telegraph days

private fun enclosedIdeographicSupplement() = Table()
        .then((0x1f260..0x1f265).toTable { it.name.substringAfterLast(' ').title() }) // Symbols for Chinese folk religion

private fun tangsa() = block(TANGSA).and(category(OTHER_LETTER))
        .toTable { it.name.substringAfterLast(' ').removeSuffix("A").lower() }

private fun znamennyMusicalNotation() = block(ZNAMENNY_MUSICAL_NOTATION).toTable { if (" NEUME " in it.name) "-" else "" }

private fun cyproMinoan() = block(CYPRO_MINOAN).toTable { it.name.substringAfterLast(' ').removePrefix("CM").stripLeading('0').lower() }
