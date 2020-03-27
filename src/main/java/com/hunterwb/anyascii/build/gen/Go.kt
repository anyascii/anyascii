package com.hunterwb.anyascii.build.gen

import java.nio.file.Files
import java.nio.file.Path

fun go(g: Generator) {
    val dataDir = Path.of("go/data")
    dataDir.toFile().deleteRecursively()
    Files.createDirectories(dataDir)

    for ((blockNum, bytes) in g.blockPointers) {
        val s = "%03x".format(blockNum)
        Files.newBufferedWriter(dataDir.resolve("$s.go")).use { writer ->
            writer.write("package data;const X$s=\"")
            for (b in bytes) {
                writer.write(BYTE_STRINGS[b.toInt() and 0xFF])
            }
            writer.write('"'.toInt())
        }
    }

    Files.newBufferedWriter(Path.of("go/strings.go")).use { w ->
        w.write("package anyascii\n\n")
        w.write("const Strings = ")
        w.write('"'.toInt())
        w.write(g.stringsBank.replace("\\", "\\\\").replace("\"", "\\\""))
        w.write('"'.toInt())
    }

    Files.newBufferedWriter(Path.of("go/block.go")).use { writer ->
        writer.write("package anyascii\n\n")
        writer.write("import \"github.com/hunterwb/any-ascii/go/data\"\n\n")
        writer.write("func Block(blockNum uint32) string {\n")
        writer.write("\tswitch blockNum {\n")
        for (block in g.blocks.keys) {
            val s = "%03x".format(block)
            writer.write("\tcase 0x$s: return data.X$s\n")
        }
        writer.write("\tdefault: return \"\"\n")
        writer.write("\t}\n")
        writer.write("}\n")
    }
}

private val BYTE_STRINGS = Array(256) { byteAsString(it.toByte()) }

private fun byteAsString(b: Byte): String {
    val n = b.toInt() and 0xFF
    val c = n.toChar()
    return when  {
        c == '\\' -> "\\\\"
        c == '"' -> "\\\""
        n in 0x20..0x7e -> c.toString()
        c == '\t' -> "\t"
        c == '\n' -> "\\n"
        c == '\r' -> "\\r"
        c == '\b' -> "\\b"
        n == 0x07 -> "\\a"
        n == 0x0b -> "\\v"
        n == 0x0c -> "\\f"
        else -> "\\x%02x".format(n)
    }
}