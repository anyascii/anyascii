package com.hunterwb.anyascii.build

import com.ibm.icu.lang.UScript
import java.nio.file.Path

fun ccCedict() = Table().apply {
    forEachLine(Path.of("input/cedict_ts.u8")) { line ->
        if (line.startsWith('#')) return@forEachLine
        val split = line.split(' ', limit = 3)
        val pronunciation = split[2].drop(1).takeWhile { it != ']' }
        val syllables = pronunciation.split(' ').map { it.filter { it.isLetter() }.capitalize() }

        fun add(cps: IntArray) {
            if (cps.size == syllables.size) {
                for (i in cps.indices) {
                    val cp = cps[i]
                    val syllable = syllables[i]
                    if (script(cp) == UScript.HAN && syllable != "Xx") {
                        putIfAbsent(cp, syllable)
                    }
                }
            } else if (cps.size == 1) {
                val cp = cps.single()
                if (script(cp) == UScript.HAN) {
                    putIfAbsent(cp, syllables.joinToString(""))
                }
            }
        }

        add(split[0].codePointsArray())
        add(split[1].codePointsArray())
    }
}