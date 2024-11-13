package com.anyascii.build

import java.nio.file.Path
import kotlin.io.path.forEachLine

fun egyptianHieroglyphs() = unikemet()
        .then(Table("egyptian-hieroglyph-format-controls"))

private fun unikemet() = Table().apply {
    Path.of("input/Unikemet.txt").forEachLine { line ->
        if (line.startsWith('#')) return@forEachLine
        val split = line.split('\t')
        check(split.size == 3)
        if (split[1] != "kEH_UniK") return@forEachLine
        val cp = parseUCodePoint(split[0])

        val (p, n, s) = CATALOG_INDEX.findOnly(split[2].substringAfter(' ')).destructured
        this[cp] = p + n.stripLeading('0') + s
    }
}

private val CATALOG_INDEX = "([A-Z]{1,2})(\\d{2,3})([A-Z]{0,2})".toRegex()
