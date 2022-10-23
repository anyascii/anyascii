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
            .then(emojis())
            .then(custom())
            .then(unihan())
            .normalize(::nfkc)
            .normalize(::nfkd)
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
        .then(block(VARIATION_SELECTORS).plus(block(VARIATION_SELECTORS_SUPPLEMENT)).toTable { "" })
        .then(block(HALFWIDTH_AND_FULLWIDTH_FORMS).normalize(::dm))
        .then(Table("spacing-modifier-letters"))
        .then(Table("currency-symbols"))
        .then(Table("letterlike-symbols"))
        .then(Table("general-punctuation"))
        .then(Table("nko"))
        .then(Table("miscellaneous-mathematical-symbols-a"))
        .then(Table("miscellaneous-mathematical-symbols-b"))
        .then(Table("kanbun"))
        .then(Table("tags"))
        .then(Table("kangxi-radicals"))
        .then(block(CJK_STROKES).toTable { it.name.substringAfterLast(' ') })
        .then(block(YI_SYLLABLES).toTable { it.name.substringAfterLast(' ').lower().takeUnless { it == "wu" } ?: "w" })
        .then(block(YI_RADICALS).toTable { it.name.substringAfterLast(' ') })
        .then(Table("vai").then(readSyllableTable("vai")))
        .then(Table("ethiopic").then(readSyllableTable("ethiopic")))
        .then(dominoes())
        .then(Table("optical-character-recognition"))
        .then(Table("ol-chiki"))
        .then(Table("cyrillic").cased(script(UScript.CYRILLIC)))
        .then(Table("coptic"))
        .then(Table("yijing"))
        .then(Table("box-drawing"))
        .then(block(BLOCK_ELEMENTS).toTable { "#" })
        .then(Table("control-pictures"))
        .then(Table("bopomofo"))
        .then(Table("hebrew"))
        .then(block(CYPRIOT_SYLLABARY).toTable { it.name.substringAfterLast(' ').lower() })
        .then(Table("braille").then(block(BRAILLE_PATTERNS).toTable { "{${it.name.substringAfterLast('-')}}" }))
        .then(Table("gothic"))
        .then(Table("lydian"))
        .then(Table("lycian"))
        .then(Table("georgian").transliterate())
        .then(Table("armenian"))
        .then(Table("thai"))
        .then(Table("number-forms"))
        .then(Table("latin").cased(script(UScript.LATIN)))
        .then(greek())
        .then(Table("kana"))
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
        .then(Table("glagolitic"))
        .then(Table("philippine"))
        .then(Table("khmer"))
        .then(Table("ogham"))
        .then(Table("thaana"))
        .then(Table("myanmar"))
        .then(Table("phags-pa"))
        .then(block(IDEOGRAPHIC_DESCRIPTION_CHARACTERS).toTable { "+" })
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
        .then(Table("hatran"))
        .then(Table("imperial-aramaic"))
        .then(Table("nabataean"))
        .then(Table("palmyrene"))
        .then(Table("elymaic"))
        .then(Table("inscriptional-parthian"))
        .then(Table("inscriptional-pahlavi"))
        .then(Table("psalter-pahlavi"))
        .then(Table("old-sogdian"))
        .then(Table("sogdian"))
        .then(Table("chorasmian"))
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
        .then(bamum())
        .then(Table("syloti-nagri"))
        .then(Table("kayah-li"))
        .then(Table("rejang"))
        .then(Table("cham"))
        .then(Table("tai-viet"))
        .then(Table("javanese"))
        .then(Table("batak"))
        .then(Table("sundanese"))
        .then(script(UScript.CHEROKEE).and(category(LOWERCASE_LETTER)).toTable { it.name.substringAfterLast(' ').lower() })
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
        .then(Table("takri"))
        .then(Table("dogra"))
        .then(Table("khudawadi"))
        .then(Table("nandinagari"))
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
        .then(Table("phaistos-disc"))
        .then(Table("old-turkic"))
        .then(Table("ahom"))
        .then(Table("zanabazar-square"))
        .then(Table("bassa-vah"))
        .then(Table("yezidi"))
        .then(Table("old-hungarian"))
        .then(Table("enclosed-alphanumeric-supplement"))
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
        .then(Table("ancient-greek-musical-notation"))
        .then(Table("duployan"))
        .then(Table("khitan-small-script"))
        .then(tangut())
        .then(Table("musical-symbols"))
        .then(block(BYZANTINE_MUSICAL_SYMBOLS).toTable { "-" })
        .then(Table("cjk-symbols-and-punctuation"))
        .then(Table("enclosed-cjk-letters-and-months"))
        .then(Table("cjk-compatibility"))
        .then(Table("cjk-compatibility-forms").then(block(CJK_COMPATIBILITY_FORMS).normalize(::dm)))
        .then(Table("enclosed-ideographic-supplement"))
        .then(Table("old-uyghur"))
        .then(Table("vithkuqi"))
        .then(Table("toto"))
        .then(Table("tangsa"))
        .then(Table("znamenny-musical-notation"))
        .then(cyproMinoan())
        .then(Table("nag-mundari"))
        .then(Table("kawi"))

private fun dominoes() = (0x1f030..0x1f093).toTable {
    val name = it.name.removePrefix("DOMINO TILE ")
    if ("BACK" in name) return@toTable "-"
    return@toTable name.split('-').let { it[1].takeLast(1) + it[2].takeLast(1) }
}

private fun nushu() = Table().apply {
    Path.of("input/NushuSources.txt").forEachLine { line ->
        if (line.isEmpty() || line.startsWith('#')) return@forEachLine
        val split = line.split('\t', limit = 3)
        if (split[1] != "kReading") return@forEachLine
        val cp = parseUCodePoint(split[0])
        this[cp] = split[2].filter(ASCII_LETTERS)
    }
}

private val ANATOLIAN_CATALOGUE_ID = "A(\\d{3}[A-Z]?)".toRegex()

private fun anatolianHieroglyphs() = block(ANATOLIAN_HIEROGLYPHS).toTable {
    ANATOLIAN_CATALOGUE_ID.findOnly(it.name).groupValues[1].stripLeading('0').lower()
}

private fun cyproMinoan() = block(CYPRO_MINOAN).toTable { it.name.substringAfterLast(' ').removePrefix("CM").stripLeading('0').lower() }

private fun canadianSyllabics() = Table("unified-canadian-aboriginal-syllabics")
        .then(readSyllableTable("ucas"))
        .then(readSyllableTable("ucas-carrier"))
        .then(readSyllableTable("ucas-blackfoot"))
        .then(readSyllableTable("ucas-inuktitut"))
