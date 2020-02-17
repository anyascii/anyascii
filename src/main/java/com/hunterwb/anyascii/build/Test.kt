package com.hunterwb.anyascii.build

import java.util.TreeSet

fun main() {
    val cps = codePoints("Latn").toCollection(TreeSet())
    for (c in cps.toTypedArray()) {
        val l = lower(c)
        if (l in cps) {
            cps.remove(c)
        }
    }
    for (c in cps.toTypedArray()) {
        val n = NFKD.normalize(toString(c))
        if (n.all { it.toInt() in cps }) {
            cps.remove(c)
        }
    }
}