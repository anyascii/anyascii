package com.hunterwb.anyascii.build

import com.ibm.icu.text.Transliterator
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

private fun unihan(key: String) = Table().apply {
    forEachLine(Path.of("input/Unihan_Readings.txt")) { line ->
        if (line.isEmpty() || line.startsWith('#')) return@forEachLine
        val split = line.split('\t', limit = 3)
        if (split[1] != key) return@forEachLine
        val cp = split[0].drop(2).toInt(16)
        var output = split[2]
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
        put(cp, output)
    }
}

private fun Table.variants() = apply {
    forEachLine(Path.of("input/Unihan_Variants.txt")) { line ->
        if (line.isEmpty() || line.startsWith('#')) return@forEachLine
        val split = line.split('\t')
        if (split.size != 3) return@forEachLine
        val cp1 = split[0].drop(2).toInt(16)
        for (s in split[2].split(' ')) {
            val cp2 = s.substringBefore('<').drop(2).toInt(16)
            if (cp1 in this) putIfAbsent(cp2, getValue(cp1))
            if (cp2 in this) putIfAbsent(cp1, getValue(cp2))
        }
    }
}