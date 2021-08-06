package com.anyascii.build.gen

import java.nio.file.Files
import java.nio.file.Path

fun go(g: Generator) {
    Files.newBufferedWriter(Path.of("impl/go/bank.go")).use { w ->
        w.write("package anyascii\n\n")
        w.write("const bank = \"")
        w.write(g.stringsBank.replace("\\", "\\\\").replace("\"", "\\\""))
        w.write("\"\n")
    }

    Files.newBufferedWriter(Path.of("impl/go/block.go")).use { w ->
        w.write("package anyascii\n\n")
        w.write("func block(blockNum uint32) string {\n")
        w.write("\tswitch blockNum {\n")
        val bs = g.blockPointers.values.iterator()
        for (block in g.blocks.keys) {
            val s = "%03x".format(block)
            w.write("\tcase 0x$s:\n\t\treturn \"")
            for (b in bs.next()) {
                w.write(BYTE_STRINGS[b.toInt() and 0xff])
            }
            w.write("\"\n")
        }
        w.write("\tdefault:\n\t\treturn \"\"\n")
        w.write("\t}\n")
        w.write("}\n")
    }
}

private val BYTE_STRINGS = Array(256) {
    val c = it.toChar()
    if (it !in 0x20..0x7e || c == '\\' || c == '"') {
        "\\x%02x".format(it)
    } else {
        c.toString()
    }
}