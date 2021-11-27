package com.anyascii.build

import com.ibm.icu.text.Normalizer2
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.Deflater
import java.util.zip.DeflaterOutputStream

fun <K, V> MutableMap<K, V>.putAllIfAbsent(other: Map<K, V>) {
    for ((k, v) in other) putIfAbsent(k, v)
}

val NFKC: Normalizer2 = Normalizer2.getNFKCInstance()

val NFKD: Normalizer2 = Normalizer2.getNFKDInstance()

val NFD: Normalizer2 = Normalizer2.getNFDInstance()

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
    val out = ByteArrayOutputStream(buf.size)
    DeflaterOutputStream(out, deflater).use { buf.inputStream().transferTo(it) }
    deflater.end()
    return out.toByteArray()
}