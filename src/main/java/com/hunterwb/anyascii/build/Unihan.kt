package com.hunterwb.anyascii.build

import com.ibm.icu.text.Transliterator
import java.nio.file.Files
import java.nio.file.Path

fun unihan() = Table()
        .then(unihan("kMandarin"))
        .then(unihan("kCantonese"))
        .then(unihan("kVietnamese"))
        .then(unihan("kJapaneseOn"))
        .then(unihan("kJapaneseKun"))
        .then(unihan("kHanyuPinyin"))
        .then(unihan("kHangul"))

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
                    }
                }
                table[cp] = output
            }
    reader.close()
    return table
}