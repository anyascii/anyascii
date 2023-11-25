package com.anyascii.build

import java.nio.file.Path
import kotlin.io.path.forEachLine

fun bamum() = Table("bamum").then(letters())

private fun letters() = Table().apply {
    Path.of("input/bamum.csv").forEachLine { line ->
        val split = line.split(',')
        val cp = CodePoint(split[0])
        this[cp] = CHARS.transliterateAny(split[1])
    }
}

private val CHARS = Table(mapOf(
        CodePoint("ə") to "e",
        CodePoint("ɛ") to "e",
        CodePoint("ɔ") to "o",
        CodePoint("ü") to "u",
        CodePoint("ɯ") to "u",
        CodePoint("ɣ") to "gh",
        CodePoint("ŋ") to "ng",
        CodePoint("ʃ") to "sh",
        CodePoint("ʒ") to "j",
        CodePoint("ʔ") to "'",
))
