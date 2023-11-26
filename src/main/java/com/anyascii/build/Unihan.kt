package com.anyascii.build

import java.nio.file.Path
import java.util.TreeMap
import kotlin.io.path.forEachLine

fun unihan() = Table()
        .add("kMandarin")
        .add("kCantonese")
        .add("kHangul")
        .add("kJapanese")
        .add("kXHC1983")
        .add("kHanyuPinyin")
        .add("kVietnamese")
        .add("kJapaneseOn")
        .add("kJapaneseKun")
        .variants()

private fun Table.add(key: String) = apply {
    val s1 = size
    val u = unihan(key)
    then(u)
    val s2 = size
    println("$key: ${s2 - s1}/${u.size}")
}

private fun unihan(key: String) = Table().apply {
    Path.of("input/Unihan_Readings.txt").forEachLine { line ->
        if (line.isEmpty() || line.startsWith('#')) return@forEachLine
        val split = line.split('\t')
        check(split.size == 3)
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
    val keys = TreeMap<String, Int>().withDefault { 0 }
    Path.of("input/Unihan_Variants.txt").forEachLine { line ->
        if (line.isEmpty() || line.startsWith('#')) return@forEachLine
        val split = line.split('\t')
        check(split.size == 3)
        val key = split[1]
        keys[key] = keys.getValue(key)
        val cp1 = parseUCodePoint(split[0])
        for (s in split[2].split(' ')) {
            val cp2 = parseUCodePoint(s.substringBefore('<'))
            val v1 = this[cp1]
            val v2 = this[cp2]
            if (v1 != null && v2 == null) {
                this[cp2] = v1
                keys[key] = keys.getValue(key) + 1
            } else if (v1 == null && v2 != null) {
                this[cp1] = v2
                keys[key] = keys.getValue(key) + 1
            }
        }
    }
    println(keys)
}
