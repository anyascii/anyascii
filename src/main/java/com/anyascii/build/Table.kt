package com.anyascii.build

import com.ibm.icu.text.Normalizer2
import java.nio.file.Path
import java.util.TreeMap
import kotlin.io.path.bufferedWriter
import kotlin.io.path.forEachLine

typealias Table = TreeMap<CodePoint, String>

fun Table.write(path: String) = apply {
    Path.of(path).bufferedWriter().use {
        for ((cp, r) in this) {
            check(PRINTABLE_ASCII.containsAll(r))
            it.append(String(cp)).append('\t').append(r).append('\n')
        }
    }
}

fun Table(file: String) = Table().apply {
    Path.of("input/tables/$file.tsv").forEachLine { line ->
        if (line.startsWith('#')) return@forEachLine
        val cp = line.codePointAt(0)
        val i = Character.charCount(cp)
        check(line[i] == '\t')
        put(cp, line.substring(i + 1))
    }
}

fun Iterable<CodePoint>.normalize(normalizer2: Normalizer2) = Table().apply {
    for (cp in this@normalize) {
        val a = String(cp)
        val b = normalizer2.normalize(a)
        if (a != b) put(cp, b)
    }
}

fun Table.normalize(normalizer2: Normalizer2) = apply {
    for (cp in ALL) {
        if (cp in this) continue
        val output = transliterate(normalizer2.normalize(cp))
        if (output != null) {
            this[cp] = output
        }
    }
}

inline fun Iterable<CodePoint>.toTable(map: (CodePoint) -> String) = associateWithTo(Table(), map)

inline fun Iterable<CodePoint>.alias(nameMap: (String) -> String) = associateWithTo(Table()) { cp ->
    val name2 = nameMap(cp.name)
    String(codePointFromName(name2) ?: cp)
}

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
            this[l]?.let { this[cp] = it.title() }
        }
    }
}

fun Table.transliterate() = apply {
    var f: Boolean
    do {
        f = false
        for (e in iterator()) {
            if (!ASCII.containsAll(e.value)) {
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
        sb.append(get(cp) ?: String(cp))
    }
    return sb.toString()
}

fun Iterable<CodePoint>.intValues() = Table().apply {
    for (cp in this@intValues) {
        val n = cp.numericValue ?: continue
        this[cp] = n.toInt().toString()
    }
}