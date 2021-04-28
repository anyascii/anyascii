package com.anyascii.build

import com.ibm.icu.lang.UCharacter.UnicodeBlock
import com.ibm.icu.text.Normalizer2
import java.nio.file.Files
import java.nio.file.Path
import java.util.IntSummaryStatistics
import java.util.TreeMap

typealias Table = TreeMap<CodePoint, String>

fun Table(vararg pairs: Pair<CodePoint, String>) = pairs.toMap(Table())

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
    for (cp in ALL_CODE_POINTS) {
        if (cp in this) continue
        val output = transliterate(normalizer2.normalize(cp.asString()))
        if (output != null) {
            this[cp] = output
        }
    }
}

inline fun Iterable<CodePoint>.toTable(map: (CodePoint) -> String): Table = associateWithTo(Table(), map)

inline fun Iterable<CodePoint>.alias(nameMap: (String) -> String): Table = associateWithTo(Table()) { cp ->
    val name2 = nameMap(cp.name)
    val cp2 = codePoint(name2) ?: cp
    cp2.asString()
}

inline fun UnicodeBlock.toTable(map: (CodePoint) -> String): Table = codePoints().toTable(map)

inline fun UnicodeBlock.alias(nameMap: (String) -> String): Table = codePoints().alias(nameMap)

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

fun Table.retain(codePoints: Iterable<CodePoint>) = apply { keys.retainAll(codePoints) }

fun Table.transliterate() = apply {
    var f: Boolean
    do {
        f = false
        for (e in iterator()) {
            if (!e.value.isAscii()) {
                f = true
                e.setValue(checkNotNull(transliterate(e.value)))
            }
        }
    } while (f)
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

fun Iterable<CodePoint>.intValues() = Table().apply {
    for (cp in this@intValues) {
        val n = cp.numericValue
        if (n.isNaN()) continue
        this[cp] = n.toInt().toString()
    }
}