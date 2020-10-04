package com.anyascii.build

import com.ibm.icu.text.Normalizer2
import com.ibm.icu.text.RuleBasedNumberFormat
import java.nio.file.Files
import java.nio.file.Path
import java.util.Locale
import java.util.zip.Deflater


fun <K, V> MutableMap<K, V>.putAllIfAbsent(other: Map<K, V>) {
    for ((k, v) in other) putIfAbsent(k, v)
}

val NFKC: Normalizer2 = Normalizer2.getNFKCInstance()

val NFKD: Normalizer2 = Normalizer2.getNFKDInstance()

val NFD: Normalizer2 = Normalizer2.getNFDInstance()

val ROMAN_NUMERALS = mapOf(1 to "I", 5 to "V", 10 to "X", 50 to "L", 100 to "C")

val NUMBER_SPELLOUT = RuleBasedNumberFormat(Locale.ENGLISH, RuleBasedNumberFormat.SPELLOUT)

inline fun forEachLine(file: Path, f: (String) -> Unit) {
    Files.newBufferedReader(file).use { reader ->
        while (true) {
            f(reader.readLine() ?: break)
        }
    }
}

fun Regex.findOnly(input: CharSequence): MatchResult = findAll(input).single()

fun deflate(buf: ByteArray): ByteArray {
    val deflater = Deflater(Deflater.BEST_COMPRESSION, true)
    deflater.setInput(buf)
    deflater.finish()
    val dst = ByteArray(buf.size)
    val size = deflater.deflate(dst)
    deflater.end()
    check(deflater.finished())
    return dst.copyOf(size)
}