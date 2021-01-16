package com.anyascii.build

import com.ibm.icu.lang.UScript

fun greek() = Table()
        .then(greekMath())
        .then(Table("greek"))
        .cased(codePoints(UScript.GREEK))
        .then(codePoints(UScript.GREEK).filterName { it.contains("WITH DASIA") }.toTable {
            val baseName = it.name.substringBefore(" WITH")
            val base = CodePoint(baseName).asString()
            if ("RHO" in baseName) {
                "${base}h"
            } else {
                if ("CAPITAL" in baseName) "H${base.lower()}" else "h$base"
            }
        })
        .then(codePoints(UScript.GREEK).filterName { it.startsWith("GREEK LETTER SMALL CAPITAL") }.alias { it.replace("LETTER SMALL CAPITAL", "CAPITAL LETTER") })

private val GREEK_MATH = ((0x1d6a8..0x1d7cb) + 0x2207 + 0x2202 + 0x3f4 + 0x3f5 + 0x3d1 + 0x3f0 + 0x3d5 + 0x3f1 + 0x3d6 + 0x3d0).toList()

private fun greekMath() = Table("greek-math")
        .cased(codePoints(UScript.GREEK))
        .then(GREEK_MATH.normalize(NFKC))
        .transliterate()
        .retain(GREEK_MATH)