package com.anyascii.build.gen

import com.anyascii.build.ASCII
import com.anyascii.build.Table
import com.anyascii.build.plane
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
    elixir(g)
}

class Generator(val table: Table) {

    val bank0 = superstring(table.filter { it.key.plane != 1 && it.value.length > 3 }.values)

    val bank1 = superstring(table.filter { it.key.plane == 1 && it.value.length > 3 }.values)

    init {
        println("bank0: ${bank0.length} ${bank0.take(20)}...${bank0.takeLast(20)}")
        println("bank1: ${bank1.length} ${bank1.take(20)}...${bank1.takeLast(20)}")
        println("banks: ${bank0.length + bank1.length}")
        check(bank0.length <= 0xffff && bank1.length <= 0xffff)
        check(table.values.all { it.length <= 0x7f })
    }

    val blocks = blocks()

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
                        val plane = block.keys.mapTo(HashSet()) { it.plane }.single()
                        val bank = if (plane == 1) { bank1 } else { bank0 }
                        val i = bank.indexOf(s)
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