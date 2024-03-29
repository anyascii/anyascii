package com.anyascii.build

import com.ibm.icu.text.Normalizer2
import com.ibm.icu.text.Normalizer2.*
import java.io.ByteArrayOutputStream
import java.lang.invoke.MethodHandles
import java.util.zip.Deflater
import java.util.zip.DeflaterOutputStream

fun <K, V, M: MutableMap<K, V>> M.then(other: Map<K, V>) = apply { for ((k, v) in other) putIfAbsent(k, v) }

fun <K, V, M: MutableMap<K, V>> M.then(key: K, value: V) = apply { putIfAbsent(key, value) }

fun <K, M: MutableMap<K, *>> M.retain(keys: Set<K>) = apply { this.keys.retainAll(keys) }

fun <K, M: MutableMap<K, *>> M.remove(keys: Set<K>) = apply { this.keys.removeAll(keys) }

fun nfkc(codePoint: CodePoint) = getNFKCInstance().normalize(codePoint)

fun nfkd(codePoint: CodePoint) = getNFKDInstance().normalize(codePoint)

fun nfd(codePoint: CodePoint) = getNFDInstance().normalize(codePoint)

fun dm(codePoint: CodePoint) = getNFKDInstance().getRawDecomposition(codePoint) ?: String(codePoint)

fun Normalizer2.normalize(codePoint: CodePoint): String = normalize(String(codePoint))

fun Regex.findOnly(input: CharSequence): MatchResult = findAll(input).single()

fun deflate(buf: ByteArray): ByteArray {
    val deflater = Deflater(Deflater.BEST_COMPRESSION, true)
    val out = ByteArrayOutputStream(buf.size)
    DeflaterOutputStream(out, deflater).use { buf.inputStream().transferTo(it) }
    deflater.end()
    return out.toByteArray()
}

fun deflate(s: String) = deflate(s.toByteArray())

inline val javaClass: Class<*> get() = MethodHandles.lookup().lookupClass()
