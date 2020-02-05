package com.hunterwb.anyascii.build

import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.lang.StringBuilder
import java.nio.file.Files
import java.nio.file.Path
import java.util.TreeSet

fun go(table: Table) {
    check(table.lengthStatistics().max < 32)
    val bank = StringBuilder()
    for (s in table.values.toSet().sortedByDescending { it.length }) {
        if (s.length > 3 && s !in bank) {
            bank.append(s)
        }
    }
    val strings = bank.toString()
    check((strings.length shr 16) == 0)
    writeStrings(strings, Path.of("go/strings.go"))
    val dataDir = Path.of("go/data")
    dataDir.toFile().deleteRecursively()
    Files.createDirectories(dataDir)
    val blocks = TreeSet<Int>()
    for (b in 0..0xFFF) {
        val block = List(256) { table[(b shl 8) or it] }.dropLastWhile { it == null }
        if (block.isEmpty()) continue
        blocks.add(b)
        val blockStream = ByteArrayOutputStream()
        val d = DataOutputStream(blockStream)
        for (s in block) {
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
                val i = strings.indexOf(s)
                d.writeShort(i)
                d.writeByte(s.length)
            }
        }
        writeBlock(b, blockStream.toByteArray(), dataDir.resolve("%03x.go".format(b)))
    }
    writeSwitch(blocks, Path.of("go/block.go"))
}

private fun writeSwitch(blocks: Set<Int>, path: Path) {
    Files.newBufferedWriter(path).use { writer ->
        writer.write("package anyascii\n\n")
        writer.write("import \"github.com/hunterwb/any-ascii/go/data\"\n\n")
        writer.write("func Block(blockNum uint16) string {\n")
        writer.write("\tswitch blockNum {\n")
        for (block in blocks) {
            val s = "%03x".format(block)
            writer.write("\tcase 0x$s: return data.X$s\n")
        }
        writer.write("\tdefault: return \"\"\n")
        writer.write("\t}\n")
        writer.write("}\n")
    }
}

private fun writeStrings(strings: String, path: Path) {
    Files.newBufferedWriter(path).use { writer ->
        writer.write("package anyascii\n\n")
        writer.write("const Strings = ")
        writer.write('"'.toInt())
        writer.write(strings.replace("\\", "\\\\").replace("\"", "\\\""))
        writer.write('"'.toInt())
    }
}

private fun writeBlock(blockNum: Int, block: ByteArray, path: Path) {
    val s = "%03x".format(blockNum)
    Files.newBufferedWriter(path).use { writer ->
        writer.write("package data\n\n")
        writer.write("const X$s = ")
        writer.write('"'.toInt())
        for (b in block) {
            writer.write("\\x")
            writer.write(java.lang.Byte.toUnsignedInt(b).toString(16).padStart(2, '0'))
        }
        writer.write('"'.toInt())
    }
}