package com.anyascii.build

import java.nio.file.Files
import java.nio.file.Path

// ala-lc 2011

fun vai() = Table("vai").then(syllables())

private fun syllables() = Table().apply {
    Files.newBufferedReader(Path.of("input/vai.csv")).use { r ->
        val vowels = r.readLine().split(',')
        while (true) {
            val line = r.readLine()?.split(',') ?: break
            val consonant = line[0]
            for (i in 1 until line.size) {
                val col = line[i]
                if (col.isEmpty()) continue
                this[col.toCodePoint()] = consonant + vowels[i]
            }
        }
    }
}