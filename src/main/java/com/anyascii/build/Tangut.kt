package com.anyascii.build

import com.ibm.icu.lang.UCharacter.UnicodeBlock
import java.nio.file.Path

fun tangut() = characters().then(components())

private fun characters() = Table().apply {
    val sources = HashMap<CodePoint, String>()
    forEachLine(Path.of("input/TangutSources.txt")) { line ->
        if (line.startsWith('#') || line.isEmpty()) return@forEachLine
        val split = line.split('\t')
        if (split[1] == "kTGT_MergedSrc") {
            val cp = split[0].substring(2).toInt(16)
            sources[cp] = split[2]
        }
    }

    forEachLine(Path.of("input/tangutdb-1-3-1.csv")) { line ->
        if (line.startsWith("LFW")) return@forEachLine
        val row = line.split(',')
        val id = row[0]
        val num = id.filter { it.isDigit() }
        val regex = if (id.endsWith('a')) {
            "L1997-$num".toRegex()
        } else {
            "L2008-.*$num.*".toRegex()
        }
        for ((cp, src) in sources) {
            if (regex.matches(src)) {
                putIfAbsent(cp, row[1])
            }
        }
    }
}

private fun components() = UnicodeBlock.TANGUT_COMPONENTS.toTable { it.name.substringAfterLast('-').stripLeading('0') }
