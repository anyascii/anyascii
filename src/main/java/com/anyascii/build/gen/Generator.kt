package com.anyascii.build.gen

import com.anyascii.build.ASCII
import com.anyascii.build.Table
import com.anyascii.build.superstring
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

    val stringsBank = superstring(table.values.filter { it.length > 3 })

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
                        val i = stringsBank.indexOf(s)
                        check(i != -1)
                        d.writeShort(i)
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