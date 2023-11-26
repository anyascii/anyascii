package com.anyascii.build

import java.nio.file.Path
import kotlin.io.path.forEachLine

fun unihan() = Table()
        .then(unihan("kMandarin"))
        .then(unihan("kCantonese"))
        .then(unihan("kHangul"))
        .then(unihan("kJapanese"))
        .then(unihan("kXHC1983"))
        .then(unihan("kHanyuPinyin"))
        .then(unihan("kVietnamese"))
        .then(unihan("kJapaneseOn"))
        .then(unihan("kJapaneseKun"))
        .then(unihan("kTang"))
        .variants()

private fun unihan(key: String) = Table().apply {
    Path.of("input/Unihan_Readings.txt").forEachLine { line ->
        if (line.isEmpty() || line.startsWith('#')) return@forEachLine
        val split = line.split('\t', limit = 3)
        if (split[1] != key) return@forEachLine
        val cp = parseUCodePoint(split[0])
        val s = split[2]
        val r = when (key) {
            "kCantonese" -> s.substringBefore(' ').filter(ASCII_LETTERS).title()
            "kHangul" -> s.substringBefore(':')
            "kHanyuPinlu" -> s.substringBefore('(').title()
            "kHanyuPinyin" -> s.substringAfter(':').substringBefore(',').substringBefore(' ').title()
            "kJapaneseKun", "kJapaneseOn", "kMandarin", "kVietnamese" -> s.substringBefore(' ').title()
            "kTang" -> s.substringBefore(' ').removePrefix("*").title()
            "kXHC1983", "kTGHZ2013" -> s.substringAfter(':').substringBefore(' ').title()
            "kSMSZD2003Readings" -> s.substringBefore('粵').substringBefore(',').title()
            "kJapanese" -> romanizeKanaWord(s.substringBefore(' '))
            else -> error(key)
        }
        put(cp, r)
    }
}

private fun Table.variants() = apply {
    Path.of("input/Unihan_Variants.txt").forEachLine { line ->
        if (line.isEmpty() || line.startsWith('#')) return@forEachLine
        val split = line.split('\t')
        if (split.size != 3) return@forEachLine
        val cp1 = parseUCodePoint(split[0])
        for (s in split[2].split(' ')) {
            val cp2 = parseUCodePoint(s.substringBefore('<'))
            if (cp1 in this) putIfAbsent(cp2, getValue(cp1))
            if (cp2 in this) putIfAbsent(cp1, getValue(cp2))
        }
    }
}
