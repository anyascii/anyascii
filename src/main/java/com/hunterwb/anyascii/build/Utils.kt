package com.hunterwb.anyascii.build

import com.ibm.icu.text.Normalizer2
import java.nio.file.Files
import java.nio.file.Path

fun <K, V> MutableMap<K, V>.putAllIfAbsent(other: Map<K, V>) {
    for ((k, v) in other) putIfAbsent(k, v)
}

val NFKC: Normalizer2 = Normalizer2.getNFKCInstance()

val NFKD: Normalizer2 = Normalizer2.getNFKDInstance()

val NFD: Normalizer2 = Normalizer2.getNFDInstance()

val ROMAN_NUMERALS = mapOf(1 to "I", 5 to "V", 10 to "X", 50 to "L", 100 to "C")

inline fun forEachLine(file: Path, f: (String) -> Unit) {
    Files.newBufferedReader(file).use { reader ->
        while (true) {
            f(reader.readLine() ?: break)
        }
    }
}