package com.hunterwb.anyascii.build.gen

import com.hunterwb.anyascii.build.Table
import com.hunterwb.anyascii.build.isAscii
import com.hunterwb.anyascii.build.lengthStatistics
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.util.TreeMap

fun generate(table: Table) {
    val g = Generator(table)
    java(g)
    python(g)
    js(g)
    rust(g)
    go(g)
    ruby(g)
}

class Generator(val table: Table) {

    val blocks = blocks()

    val stringsBank = stringsBank()

    val blockPointers = blockPointers()

    private fun blocks(): Map<Int, List<String>> {
        val m = TreeMap<Int, List<String>>()
        for (b in 0..0xFFF) {
            val block = List(256) { table[(b shl 8) or it] ?: "" }
                    .dropLastWhile { it.isEmpty() }
                    .mapIndexed { i, s -> if (b == 0 && i.isAscii()) "" else s }
            if (block.isEmpty()) continue
            m[b] = block
        }
        return m
    }

    private fun stringsBank(): String {
        val sb = StringBuilder()
        val ss = table.values.filter { it.length > 3 }
        val ss2 = ss.filter { a -> ss.none { b -> a != b && a in b } }.sortedBy { it.length }.toCollection(LinkedHashSet())
        while (ss2.isNotEmpty()) {
            val m = ss2.maxBy { overlap(sb, it) }!!
            ss2.remove(m)
            check(m !in sb)
            sb.append(m, overlap(sb, m), m.length)
        }
        check(ss.all { it in sb })
        return sb.toString()
    }

    private fun overlap(a: CharSequence, b: String): Int {
        for (i in (b.length - 1).downTo(1)) {
            if (a.endsWith(b.substring(0, i))) {
                return i
            }
        }
        return 0
    }

    private fun blockPointers(): Map<Int, ByteArray> {
        check(table.lengthStatistics().max < 32)
        check((stringsBank.length shr 16) == 0)
        val m = TreeMap<Int, ByteArray>()
        for ((blockNum, blockStrings) in blocks) {
            val out = ByteArrayOutputStream()
            val d = DataOutputStream(out)
            for ((lo, s0) in blockStrings.withIndex()) {
                val cp = (blockNum shl 8) or lo
                val s = if (cp.isAscii()) Character.toString(cp) else s0
                when (s.length) {
                    0 -> {
                        d.writeShort(0)
                        d.writeByte(0)
                    }
                    1 -> {
                        d.writeByte(s[0].toInt())
                        d.writeByte(0)
                        d.writeByte(1)
                    }
                    2 -> {
                        d.writeByte(s[0].toInt())
                        d.writeByte(s[1].toInt())
                        d.writeByte(2)
                    }
                    3 -> {
                        d.writeByte(s[0].toInt())
                        d.writeByte(s[1].toInt())
                        d.writeByte(s[2].toInt())
                    }
                    else -> {
                        d.writeShort(stringsBank.indexOf(s))
                        d.writeByte(s.length)
                    }
                }
            }
            m[blockNum] = out.toByteArray()
        }
        return m
    }
}