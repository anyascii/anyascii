package com.anyascii.build

import com.ibm.icu.lang.UCharacterCategory
import com.ibm.icu.lang.UScript
import java.nio.file.Path

fun egyptianHieroglyphs() = manuelDeCodage()
        .then(catalogueIds())
        .then(formatControls())

private fun manuelDeCodage(): Table = Table().apply {
    forEachLine(Path.of("input/Unicode-MdC-Mapping-v1.utf8.txt")) { line ->
        val split = line.split('\t')
        val cp = split[0].toCodePoint()
        val s = split[2].split(' ')[0]
        if (s.all { it.isLetter() }) {
            this[cp] = s
        }
    }
}

private val CATALOGUE_ID = "([A-Z]{1,2})(\\d{3})([A-Z]{0,1})".toRegex()

private fun catalogueIds() = codePoints(UScript.EGYPTIAN_HIEROGLYPHS)
        .filter { it.category == UCharacterCategory.OTHER_LETTER }
        .toTable { catalogueId(it) }

private fun catalogueId(cp: CodePoint): String {
    val (prefix, num, suffix) = CATALOGUE_ID.findOnly(cp.name).destructured
    return prefix + num.stripLeading('0') + suffix.lower()
}

private fun formatControls() = Table(
        0x13430 to ":",
        0x13431 to "*",
        0x13432 to "",
        0x13433 to "",
        0x13434 to "",
        0x13435 to "",
        0x13436 to "+",
        0x13437 to "(",
        0x13438 to ")"
)