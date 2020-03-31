package com.hunterwb.anyascii.build

import com.ibm.icu.lang.UCharacter
import com.ibm.icu.text.Normalizer2
import java.nio.file.Files
import java.nio.file.Path
import java.util.IntSummaryStatistics
import java.util.TreeMap

typealias Table = TreeMap<CodePoint, String>

fun Table.then(other: Table) = apply { putAllIfAbsent(other) }

fun Table.then(codePoint: CodePoint, s: String) = apply { putIfAbsent(codePoint, s) }

fun Table.minus(codePoint: CodePoint) = apply { remove(codePoint) }

fun Table.minus(codePoints: Iterable<CodePoint>) = apply { for (cp in codePoints) remove(cp) }

fun Table.write(path: String) = apply {
    Files.newBufferedWriter(Path.of(path)).use {
        for ((cp, r) in this) {
            check(r.isEmpty() || r.isPrintableAscii())
            it.append(cp.asString()).append('\t').append(r).append('\n')
        }
    }
}

fun Table(file: String) = Table().apply {
    forEachLine(Path.of("input/tables/$file.tsv")) { line ->
        if (line.startsWith('#')) return@forEachLine
        val cp = line.codePointAt(0)
        val i = Character.charCount(cp)
        check(line[i] == '\t')
        put(cp, line.substring(i + 1))
    }
}

fun Table.normalize(normalizer2: Normalizer2, replacement: String? = null) = apply {
    for (cp in 128..Character.MAX_CODE_POINT) {
        if (cp in this) continue
        val output = transliterate(normalizer2.normalize(cp.asString()), replacement)
        if (output != null && output.isNotEmpty()) {
            this[cp] = output
        }
    }
}

private fun Table.transliterate(s: String, default: String?): String? {
    val buf = StringBuilder()
    for (cp in s.codePoints()) {
        val d = this[cp] ?: default ?: return null
        buf.append(d)
    }
    return buf.toString()
}

inline fun Iterable<CodePoint>.toTable(map: (CodePoint) -> String): Table = associateWithTo(Table(), map)

fun Table.lengthStatistics() = IntSummaryStatistics().apply { values.forEach { accept(it.length) } }

fun Table.cased() = apply {
    for ((cp, s) in this.toMap()) {
        putIfAbsent(cp.lower(), s.lower())
        putIfAbsent(cp.upper(), s.capitalize())
    }
    for (cp in 0..Character.MAX_CODE_POINT) {
        if (cp in this) continue
        this[cp.upper()]?.let { putIfAbsent(cp, it.lower()) }
        this[cp.lower()]?.let { putIfAbsent(cp, it.capitalize()) }
    }
}

fun Table.aliasing(codePoints: Iterable<CodePoint>, nameTransform: (String) -> String) = apply {
    for (cp in codePoints) {
        val cp2 = UCharacter.getCharFromName(nameTransform(cp.name))
        check(cp2 != -1) { cp.toString(16) }
        putIfAbsent(cp, getValue(cp2))
    }
}

fun Table.retain(codePoints: Iterable<CodePoint>) = apply { keys.retainAll(codePoints) }