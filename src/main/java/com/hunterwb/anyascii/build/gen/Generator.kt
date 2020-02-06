package com.hunterwb.anyascii.build.gen

import com.hunterwb.anyascii.build.Table
import com.hunterwb.anyascii.build.lengthStatistics
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.util.TreeMap

class Generator(val table: Table) {

    val blocks = blocks()

    val stringsBank = stringsBank()

    val blockPointers = blockPointers()

    private fun blocks(): Map<Int, List<String?>> {
        val m = TreeMap<Int, List<String?>>()
        for (b in 0..0xFFF) {
            val block = List(256) { table[(b shl 8) or it] }.dropLastWhile { it == null }
            if (block.isEmpty()) continue
            m[b] = block
        }
        return m
    }

    private fun stringsBank(): String {
        val sb = StringBuilder()
        for (s in table.values.toSet().sortedByDescending { it.length }) {
            if (s.length > 3 && s !in sb) {
                sb.append(s)
            }
        }
        return sb.toString()
    }

    private fun blockPointers(): Map<Int, ByteArray> {
        check(table.lengthStatistics().max < 32)
        check((stringsBank.length shr 16) == 0)
        val m = TreeMap<Int, ByteArray>()
        for ((n, ss) in blocks) {
            val out = ByteArrayOutputStream()
            val d = DataOutputStream(out)
            for (s in ss) {
                if (s == null) {
                    d.writeShort(0)
                    d.writeByte(0)
                } else if (s.length == 1) {
                    d.writeByte(s[0].toInt())
                    d.writeByte(0)
                    d.writeByte(1)
                } else if (s.length == 2) {
                    d.writeByte(s[0].toInt())
                    d.writeByte(s[1].toInt())
                    d.writeByte(2)
                } else if (s.length == 3) {
                    d.writeByte(s[0].toInt())
                    d.writeByte(s[1].toInt())
                    d.writeByte(s[2].toInt())
                } else {
                    val i = stringsBank.indexOf(s)
                    d.writeShort(i)
                    d.writeByte(s.length)
                }
            }
            m[n] = out.toByteArray()
        }
        return m
    }
}