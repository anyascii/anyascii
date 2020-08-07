package com.anyascii.build

import com.ibm.icu.lang.UCharacter
import com.ibm.icu.text.Normalizer2
import java.nio.file.Files
import java.nio.file.Path
import java.util.IntSummaryStatistics
import java.util.TreeMap

typealias Table = TreeMap<CodePoint, String>

val ASCII: Table = (0..127).toTable { it.asString() }

fun Table.then(other: Table) = apply { putAllIfAbsent(other) }

fun Table.then(codePoint: CodePoint, s: String) = apply { putIfAbsent(codePoint, s) }

fun Table.minus(codePoint: CodePoint) = apply { remove(codePoint) }

fun Table.minus(codePoints: Iterable<CodePoint>) = apply { for (cp in codePoints) remove(cp) }

fun Table.write(path: String) = apply {
    Files.newBufferedWriter(Path.of(path)).use {
        for ((cp, r) in this) {
            check(r.isPrintableAscii())
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

fun Iterable<CodePoint>.normalize(normalizer2: Normalizer2) = Table().apply {
    for (cp in this@normalize) {
        val a = cp.asString()
        val b = normalizer2.normalize(a)
        if (a != b) put(cp, b)
    }
}

fun Table.normalize(normalizer2: Normalizer2) = apply {
    for (cp in 128..Character.MAX_CODE_POINT) {
        if (cp in this) continue
        val output = transliterate(normalizer2.normalize(cp.asString()))
        if (output != null && output.isNotEmpty()) {
            this[cp] = output
        }
    }
}

inline fun Iterable<CodePoint>.toTable(map: (CodePoint) -> String): Table = associateWithTo(Table(), map)

fun Table.lengthStatistics() = IntSummaryStatistics().apply { values.forEach { accept(it.length) } }

fun Table.cased(codePoints: Iterable<CodePoint>) = apply {
    for (cp in codePoints) {
        if (cp in this) continue
        val u = cp.upper()
        if (u != cp) {
            this[u]?.let { this[cp] = it.lower() }
            continue
        }
        val l = cp.lower()
        if (l != cp) {
            this[l]?.let { this[cp] = it.capitalize() }
        }
    }
}

fun Table.aliasing(codePoints: Iterable<CodePoint>, nameTransform: (String) -> String) = apply {
    for (cp1 in codePoints) {
        val name1 = cp1.name
        val name2 = nameTransform(name1)
        val cp2 = UCharacter.getCharFromName(name2)
        check(cp2 != -1) { "$name1 - $name2" }
        putIfAbsent(cp1, cp2.asString())
    }
}

fun Table.retain(codePoints: Iterable<CodePoint>) = apply { keys.retainAll(codePoints) }

fun Table.transliterate() = apply {
    for ((cp, r) in toMap()) {
        if (!r.isPrintableAscii()) {
            this[cp] = checkNotNull(transliterate(r))
        }
    }
}

fun Table.transliterate(s: String): String? {
    val sb = StringBuilder()
    for (cp in s.codePoints()) {
        val r = get(cp) ?: return null
        sb.append(r)
    }
    return sb.toString()
}

fun Table.transliterateAny(s: String): String {
    val sb = StringBuilder()
    for (cp in s.codePoints()) {
        val r = get(cp) ?: cp.asString()
        sb.append(r)
    }
    return sb.toString()
}