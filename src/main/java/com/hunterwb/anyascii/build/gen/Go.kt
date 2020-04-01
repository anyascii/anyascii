package com.hunterwb.anyascii.build.gen

import java.nio.file.Files
import java.nio.file.Path

fun go(g: Generator) {
    Files.newBufferedWriter(Path.of("go/strings.go")).use { w ->
        w.write("package anyascii\n\n")
        w.write("const Strings = \"")
        w.write(g.stringsBank.replace("\\", "\\\\").replace("\"", "\\\""))
        w.write("\"\n")
    }

    Files.newBufferedWriter(Path.of("go/block.go")).use { w ->
        w.write("package anyascii\n\n")
        w.write("func Block(blockNum uint32) string {\n")
        w.write("\tswitch blockNum {\n")
        val bs = g.blockPointers.values.iterator()
        for (block in g.blocks.keys) {
            val s = "%03x".format(block)
            w.write("\tcase 0x$s:\n\t\treturn ${asString(bs.next())}\n")
        }
        w.write("\tdefault:\n\t\treturn \"\"\n")
        w.write("\t}\n")
        w.write("}\n")
    }
}

private fun asString(bs: ByteArray) = '"' + bs.joinToString("") { BYTE_STRINGS[it.toInt() and 0xFF] } + '"'

private val BYTE_STRINGS = Array(256) {
    val c = it.toChar()
    when  {
        c == '\\' -> "\\\\"
        c == '"' -> "\\\""
        it in 0x20..0x7e -> c.toString()
        c == '\t' -> "\t"
        c == '\n' -> "\\n"
        c == '\r' -> "\\r"
        c == '\b' -> "\\b"
        it == 0x07 -> "\\a"
        it == 0x0b -> "\\v"
        it == 0x0c -> "\\f"
        else -> "\\x%02x".format(it)
    }
}