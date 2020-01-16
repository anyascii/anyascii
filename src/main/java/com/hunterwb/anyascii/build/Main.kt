package com.hunterwb.anyascii.build

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ibm.icu.text.Transliterator
import com.vdurmont.emoji.EmojiManager
import net.gcardone.junidecode.Junidecode
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val table = ascii()
            .then(custom())
            .nfkc()
            .then(icu("::Latin-ASCII; [:^ASCII:]>;"))
            .then(unihan())
            .then(icu("[:^Han:]>; ::Han-Latin; ::Latin-ASCII; [:^ASCII:]>; ::Any-Title;"))
            .nfkc()
            .then(unidecode())
            .then(icu("::Any-Latin; ::Latin-ASCII; [:^ASCII:]>;"))
            .nfkc()
            .minus(ascii())
            .write("table.tsv")

    java(table)
    python(table)
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

private fun junidecode(): Table {
    val table = Table()
    for (cp in 128..0xFFFF) {
        val output = Junidecode.unidecode(toString(cp))
        if (output.isNotEmpty() && !output.startsWith("[?]")) {
            table[cp] = output
        }
    }
    return table
}

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
                        check(output.isAscii())
                    }
                }
                table[cp] = output
            }
    reader.close()
    return table
}

private fun vdurmontEmojis(): Table {
    val table = Table()
    EmojiManager.getAll().forEach {
        val cps = it.unicode.codePoints().toArray()
        if (cps.size > 1) return@forEach
        table[cps[0]] = ":${it.aliases.first()}:"
    }
    return table
}

private fun githubEmojis(): Table {
    return jacksonObjectMapper()
            .readValue<Map<String, String>>(File("input/emojis.json"))
            .filterValues { it.contains("/unicode/") && !it.contains('-') }
            .mapValues { it.value.substringBeforeLast('.').substringAfterLast("/").toInt(16) }
            .toList()
            .associateTo(Table()) { it.swap() }
}

private fun ascii(): Table = (0..127).toTable { toString(it) }

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
        .then(0xa015, "w") // yi syllables
        .then((0xa000..0xa48c).toTable { name(it).substringAfterLast(' ').toLowerCase() }) // yi syllables