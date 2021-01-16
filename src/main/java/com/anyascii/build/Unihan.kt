package com.anyascii.build

import com.ibm.icu.lang.UScript
import java.nio.file.Path

fun unihan() = Table()
        .then(unihan("kMandarin"))
        .then(unihan("kCantonese"))
        .then(unihan("kHangul"))
        .then(unihan("kHanyuPinyin"))
        .then(unihan("kVietnamese"))
        .then(unihan("kJapaneseOn"))
        .then(unihan("kJapaneseKun"))
        .then(ccCedict())
        .then(unihan("kTang"))
        .variants()

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
            }
            "kTang" -> {
                output = output.removePrefix("*").split(' ')[0]
                output = output.toLowerCase().filter { it.isLetter() }.capitalize()
            }
            else -> {
                output = output.substringAfter(':')
                output = output.split(' ', ',')[0]
                if (output.last().isDigit()) output = output.dropLast(1)
                output = output.toLowerCase().capitalize()
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

private fun ccCedict() = Table().apply {
    forEachLine(Path.of("input/cedict_ts.u8")) { line ->
        if (line.startsWith('#')) return@forEachLine
        val split = line.split(' ', limit = 3)
        val pronunciation = split[2].drop(1).takeWhile { it != ']' }
        val syllables = pronunciation.split(' ').map { it.filter { it.isLetter() }.capitalize() }

        fun add(cps: IntArray) {
            if (cps.size == syllables.size) {
                for (i in cps.indices) {
                    val cp = cps[i]
                    val syllable = syllables[i]
                    if (cp.script == UScript.HAN && syllable != "Xx") {
                        putIfAbsent(cp, syllable)
                    }
                }
            } else if (cps.size == 1) {
                val cp = cps.single()
                if (cp.script == UScript.HAN) {
                    putIfAbsent(cp, syllables.joinToString(""))
                }
            }
        }

        add(split[0].codePointsArray())
        add(split[1].codePointsArray())
    }
}