package com.anyascii.build.gen

import com.anyascii.build.ASCII
import com.anyascii.build.Table
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
    cSharp(g)
    shell(g)
    php(g)
    julia(g)
    c(g)
}

class Generator(val table: Table) {

    val blocks = blocks()

    val stringsBank = stringsBank()

    val blockPointers = blockPointers()

    private fun blocks(): Map<Int, Table> {
        val m = TreeMap<Int, Table>()
        for (blockNum in 0..0xfff) {
            val block = Table()
            for (lo in 0..0xff) {
                val cp = (blockNum shl 8) or lo
                block[cp] = table[cp] ?: ""
            }
            while (block.isNotEmpty() && block.lastEntry().value.isEmpty()) {
                block.remove(block.lastKey())
            }
            if (block.isNotEmpty()) {
                m[blockNum] = block
            }
        }
        return m
    }

    private fun stringsBank(): String {
        val sb = StringBuilder()
        val ss = table.values.filter { it.length > 3 }
        val ss2 = ss.filter { a -> ss.none { b -> a != b && a in b } }.sortedBy { it.length }.toCollection(LinkedHashSet())
        while (ss2.isNotEmpty()) {
            val m = ss2.maxByOrNull { overlap(sb, it) }!!
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
        val longest = table.values.maxOf { it.length }
        check(longest <= 0x7f)
        println("$longest/${0x7f}")
        check(stringsBank.length <= 0xffff)
        println("${stringsBank.length}/${0xffff}")

        val m = TreeMap<Int, ByteArray>()
        for ((blockNum, block) in blocks) {
            val out = ByteArrayOutputStream()
            val d = DataOutputStream(out)
            for (s in block.values) {
                when (s.length) {
                    0 -> {
                        d.writeShort(0)
                        d.writeByte(0x80)
                    }
                    1 -> {
                        d.writeByte(s[0].code)
                        d.writeByte(0)
                        d.writeByte(0x81)
                    }
                    2 -> {
                        d.writeByte(s[0].code)
                        d.writeByte(s[1].code)
                        d.writeByte(0x82)
                    }
                    3 -> {
                        d.writeByte(s[0].code)
                        d.writeByte(s[1].code)
                        d.writeByte(s[2].code)
                    }
                    else -> {
                        d.writeShort(stringsBank.indexOf(s))
                        d.writeByte(0x80 or s.length)
                    }
                }
            }
            m[blockNum] = out.toByteArray()
        }
        return m
    }
}

fun Table.noAscii(): List<String> {
    val l = ArrayList<String>(size)
    for ((cp, s) in this) {
        l.add(if (cp in ASCII) "" else s)
    }
    return l
}