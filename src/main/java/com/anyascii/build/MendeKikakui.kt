package com.anyascii.build

import java.nio.file.Path
import kotlin.io.path.forEachLine

fun mendeKikakui() = Table("mende-kikakui").then(letters())

private fun letters() = Table().apply {
    Path.of("input/mende-kikakui.csv").forEachLine { line ->
        val split = line.split(',')
        val cp = CodePoint(split[0])
        this[cp] = CHARS.transliterateAny(split[1])
    }
}

private val CHARS = Table(mapOf(
        CodePoint("ɛ") to "e",
        CodePoint("ɔ") to "o",
        CodePoint("ŋ") to "ng",
        0x303 to "",
))
