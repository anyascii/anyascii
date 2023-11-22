package com.anyascii.build

import java.nio.file.Path
import java.util.TreeMap
import kotlin.io.path.forEachLine

private fun asKatakana(s: String): String {
    return s.codePointsArray().joinToString("") {
        val n = it.name.replace("HIRAGANA ", "KATAKANA ")
        val cp = codePointFromName(n) ?: it
        return@joinToString String(cp)
    }
}

private val MAP = TreeMap<String, String>(compareByDescending<String> { it.length }.thenBy { it }).apply {
    Path.of("input/kana.csv").forEachLine { line ->
        val split = line.split(',')
        this[split[0]] = split[1]
    }
}

fun romanizeKanaWord(s: String): String {
    var k = asKatakana(s)
            .replace("ン([アイウエオヤユヨ])".toRegex(), "n'$1")
            .replace("ン", "n")
            .replace("ッ([カキクケコ])".toRegex(), "k$1")
            .replace("ッ([サシスセソ])".toRegex(), "s$1")
            .replace("ッ([タチツテト])".toRegex(), "t$1")
            .replace("ッ([パピプペポ])".toRegex(), "p$1")

    MAP.forEach { (c, r) ->
        k = k.replace(c, r)
    }

    return k
            .replace("aー|aa".toRegex(), "ā")
            .replace("iー", "ii")
            .replace("uー|uu".toRegex(), "ū")
            .replace("eー|ee".toRegex(), "ē")
            .replace("oー|oo|ou".toRegex(), "ō")
            .also { check(it.all { it in "abcdefghijklmnopqrstuvwxyz'āēōū" }) { s } }
            .title()
}
