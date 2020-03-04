package com.hunterwb.anyascii.build

import com.ibm.icu.text.Transliterator
import java.nio.file.Files
import java.nio.file.Path

fun unihan() = Table()
        .then(unihan("kMandarin"))
        .then(unihan("kCantonese"))
        .then(unihan("kHanyuPinyin"))
        .then(unihan("kHangul"))
        .then(unihan("kVietnamese"))
        .then(unihan("kJapaneseOn"))
        .then(unihan("kJapaneseKun"))
        .then(unihan("kTang"))
        .variants()

private val HANGUL_TO_LATIN: Transliterator = Transliterator.getInstance("Hangul-Latin")

private val TANG_MAP: Map<Char, String> = mapOf('ɛ' to "e", 'ɑ' to "a", 'æ' to "ae", 'ə' to "e", '(' to "", ')' to "")

private fun unihan(key: String): Table {
    val table = Table()
    Files.lines(Path.of("input/Unihan_Readings.txt"))
            .filter { !it.startsWith("#") && it.isNotEmpty() }
            .map { it.split('\t', limit = 3) }
            .filter { it[1] == key }
            .forEach {
                val cp = it[0].drop(2).toInt(16)
                var output = it[2]
                when (key) {
                    "kHangul" -> {
                        output = output.substringBefore(':')
                        output = HANGUL_TO_LATIN.transliterate(output).capitalize()
                    }
                    "kTang" -> {
                        output = output.removePrefix("*").split(' ')[0]
                        output = output.removeDiacritics().toLowerCase()
                        output = output.map { TANG_MAP.getOrElse(it) { it.toString() } }.joinToString("").capitalize()
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
    return table
}

private fun Table.variants() = apply {
    Files.lines(Path.of("input/Unihan_Variants.txt"))
            .filter { !it.startsWith("#") && it.isNotEmpty() }
            .map { it.split('\t') }
            .filter { it.size == 3 }
            .forEach {
                val cp1 = it[0].drop(2).toInt(16)
                for (s in it[2].split(' ')) {
                    val cp2 = s.substringBefore('<').drop(2).toInt(16)
                    if (cp1 in this) putIfAbsent(cp2, getValue(cp1))
                    if (cp2 in this) putIfAbsent(cp1, getValue(cp2))
                }
            }
}