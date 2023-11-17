package com.anyascii.build

import com.ibm.icu.lang.UCharacter.UnicodeBlock
import com.ibm.icu.lang.UScript
import java.util.IntSummaryStatistics
import java.util.TreeMap

fun printCoverage(table: Table) {
    val total = Coverage()
    val blocks = LinkedHashMap<UnicodeBlock, Coverage>()
    val scripts = LinkedHashMap<String, Coverage>()
    val planes = LinkedHashMap<Int, Coverage>()
    val lengths = TreeMap<Int, Int>().withDefault { 0 }
    val lengthStats = IntSummaryStatistics()
    for (cp in ALL) {
        val s = table[cp]
        val present = s != null
        total.cover(present)
        blocks.cover(cp.block, present)
        scripts.cover(UScript.getName(cp.script), present)
        planes.cover(cp.plane, present)
        if (s != null) {
            lengths[s.length] = lengths.getValue(s.length) + 1
            lengthStats.accept(s.length)
        }
    }
    blocks.values.removeIf { it.missing == 0 }
    scripts.values.removeIf { it.missing == 0 }
    println("total: $total")
    println("blocks: $blocks")
    println("scripts: $scripts")
    println("planes: $planes")
    println("lengths: $lengths $lengthStats")
}

private class Coverage {

    var present = 0

    var missing = 0

    fun cover(present: Boolean) { if (present) this.present++ else missing++ }

    val total get() = present + missing

    override fun toString() = "$present:$missing|$total"
}

private fun <K> MutableMap<K, Coverage>.cover(k: K, present: Boolean) = getOrPut(k) { Coverage() }.cover(present)