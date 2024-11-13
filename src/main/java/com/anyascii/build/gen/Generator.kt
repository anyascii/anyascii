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
    elixir(g)
}

const val BANK2_LENGTH = 7

class Generator(val table: Table) {

    val bank1 = superstring(table.values.filter { it.length in 4 until BANK2_LENGTH })

    val bank2 = superstring(table.values.filter { it.length >= BANK2_LENGTH })

    init {
        println("bank1: ${bank1.length} ${bank1.take(20)}...${bank1.takeLast(20)}")
        println("bank2: ${bank2.length} ${bank2.take(20)}...${bank2.takeLast(20)}")
        check(bank1.length <= 0xffff && bank2.length <= 0xffff)
        check(table.values.maxOf { it.length } <= 0x7f)
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
                        val bank = if (s.length < BANK2_LENGTH) { bank1 } else { bank2 }
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