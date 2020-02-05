package com.hunterwb.anyascii.build

import java.io.DataOutputStream
import java.lang.StringBuilder
import java.nio.file.Files
import java.nio.file.Path
import java.util.TreeSet

fun rust(table: Table) {
    check(table.lengthStatistics().max < 32)
    val bank = StringBuilder()
    for (s in table.values.toSet().sortedByDescending { it.length }) {
        if (s.length > 3 && s !in bank) {
            bank.append(s)
        }
    }
    val strings = bank.toString()
    check((strings.length shr 16) == 0)
    Files.writeString(Path.of("rust/src/strings.txt"), strings)
    val dataDir = Path.of("rust/src/data")
    dataDir.toFile().deleteRecursively()
    Files.createDirectories(dataDir)
    val blocks = TreeSet<Int>()
    for (b in 0..0xFFF) {
        val block = List(256) { table[(b shl 8) or it] }.dropLastWhile { it == null }
        if (block.isEmpty()) continue
        blocks.add(b)
        val file = dataDir.resolve("%03x.bin".format(b))
        Files.newOutputStream(file).use { out ->
            val d = DataOutputStream(out)
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
        }
    }
    writeSwitch(blocks, Path.of("rust/src/block.rs"))
}

private fun writeSwitch(blocks: Set<Int>, path: Path) {
    Files.newBufferedWriter(path).use { writer ->
        writer.write("pub fn block(block_num: u16) -> &'static [u8] {\n")
        writer.write("\treturn match block_num {\n")
        for (block in blocks) {
            val s = "%03x".format(block)
            writer.write("\t\t0x$s => include_bytes!(\"data/$s.bin\"),\n")
        }
        writer.write("\t\t_ => &[]\n")
        writer.write("\t}\n")
        writer.write("}\n")
    }
}