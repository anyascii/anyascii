package com.anyascii.build

import com.ibm.icu.lang.UCharacter.UnicodeBlock.*
import java.nio.file.Path
import kotlin.io.path.forEachLine
import kotlin.io.path.useLines

fun tangut() = characters()
        .then(charactersFallback())
        .then(components())

private fun characters() = Table().apply {
    val sources = HashMap<CodePoint, String>()
    Path.of("input/TangutSources.txt").forEachLine { line ->
        if (line.startsWith('#') || line.isEmpty()) return@forEachLine
        val split = line.split('\t')
        if (split[1] == "kTGT_MergedSrc") {
            val cp = parseUCodePoint(split[0])
            sources[cp] = split[2]
        }
    }

    // http://amritas.com/Tangut/tangutdb-4-0.xls
    Path.of("input/tangutdb-4-0.tsv").useLines { lines ->
        lines.drop(1).forEach { line ->
            val row = line.split('\t')
            val l2008 = row[3].takeUnless { it == "-" }?.removePrefix("L")
            val regex = if (l2008 != null) {
                "L2008-.*$l2008.*".toRegex()
            } else {
                val l1997 = row[4].takeUnless { it == "-" }!!.removePrefix("L")
                "L1997-$l1997".toRegex()
            }
            for ((cp, src) in sources) {
                if (regex.matches(src)) {
                    putIfAbsent(cp, row[5])
                }
            }
        }
    }
}

private fun charactersFallback() = (block(TANGUT) + block(TANGUT_SUPPLEMENT)).toTable { "?" }

private fun components() = block(TANGUT_COMPONENTS).toTable { it.name.substringAfterLast('-').stripLeading('0') }
