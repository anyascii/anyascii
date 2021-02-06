package com.anyascii.build

import com.ibm.icu.lang.UCharacter.UnicodeBlock
import com.ibm.icu.lang.UScript

fun printCoverage(table: Table) {
    val total = Coverage()
    val blocks = LinkedHashMap<UnicodeBlock, Coverage>()
    val scripts = LinkedHashMap<String, Coverage>()
    val planes = LinkedHashMap<Int, Coverage>()
    for (cp in ALL_CODE_POINTS) {
        val present = cp in table
        total.cover(present)
        blocks.cover(cp.block, present)
        scripts.cover(UScript.getName(cp.script), present)
        planes.cover(cp.plane, present)
    }
    blocks.values.removeIf { it.missing == 0 }
    scripts.values.removeIf { it.missing == 0 }
    println("total: $total")
    println("blocks: $blocks")
    println("scripts: $scripts")
    println("planes: $planes")
}

private class Coverage {

    var present = 0

    var missing = 0

    fun cover(present: Boolean) { if (present) this.present++ else missing++ }

    val total get() = present + missing

    override fun toString() = "$present:$missing|$total"
}

private fun <K> MutableMap<K, Coverage>.cover(k: K, present: Boolean) = getOrPut(k) { Coverage() }.cover(present)