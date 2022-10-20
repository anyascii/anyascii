package com.anyascii.build

import com.ibm.icu.lang.UCharacter.UnicodeBlock.*
import java.nio.file.Path
import kotlin.io.path.forEachLine

fun egyptianHieroglyphs() = manuelDeCodage()
        .then(catalogueIds())
        .then(Table("egyptian-hieroglyph-format-controls"))

private fun manuelDeCodage() = Table().apply {
    Path.of("input/Unicode-MdC-Mapping-v1.utf8.txt").forEachLine { line ->
        val split = line.split('\t')
        val cp = CodePoint(split[0])
        val s = split[2].substringBefore(' ')
        if (ASCII_LETTERS.containsAll(s)) {
            this[cp] = s
        }
    }
}

private val CATALOGUE_ID = "([A-Z]{1,2})(\\d{3})([A-Z]{0,1})".toRegex()

private fun catalogueIds() = block(EGYPTIAN_HIEROGLYPHS).toTable { catalogueId(it) }

private fun catalogueId(cp: CodePoint): String {
    val (prefix, num, suffix) = CATALOGUE_ID.findOnly(cp.name).destructured
    return prefix + num.stripLeading('0') + suffix.lower()
}
