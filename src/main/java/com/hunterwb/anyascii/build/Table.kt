package com.hunterwb.anyascii.build

import com.ibm.icu.text.Normalizer2
import java.nio.file.Files
import java.nio.file.Path
import java.util.IntSummaryStatistics
import java.util.TreeMap

typealias Table = TreeMap<Int, String>

fun Table.then(other: Table) = apply { putAllIfAbsent(other) }

fun Table.minus(other: Table) = apply { for (cp in other.keys) remove(cp) }

fun Table.write(path: String): Table {
    Files.newBufferedWriter(Path.of(path)).use {
        for ((cp, r) in this) {
            it.append(toString(cp)).append('\t').append(r).append('\n')
        }
    }
    return this
}

fun Table(path: String): Table = Files.readAllLines(Path.of(path))
        .filter { !it.startsWith('#') }
        .map { it.split('\t') }
        .associateTo(Table()) { it[0].codePointAt(0) to it[1] }

fun Table.nfkc(): Table {
    val nfkc = Normalizer2.getNFKCInstance()
    for (cp in 128..Character.MAX_CODE_POINT) {
        if (cp in this) continue
        val output = transliterate(nfkc.normalize(toString(cp)))
        if (output != null) {
            this[cp] = output
        }
    }
    return this
}

private fun Table.transliterate(s: String): String? {
    val buf = StringBuilder()
    for (cp in s.codePoints()) {
        if (cp in this) {
            buf.append(this.getValue(cp))
        } else {
            return null
        }
    }
    return buf.toString()
}

inline fun IntRange.toTable(map: (Int) -> String): Table {
    val table = Table()
    for (cp in this) {
        table[cp] = map(cp)
    }
    return table
}

fun Table.lengthStatistics() = IntSummaryStatistics().apply { values.forEach { accept(it.length) } }