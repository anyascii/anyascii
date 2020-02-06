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
            writer.write("package data\n\n")
            writer.write("const X$s = ")
            writer.write('"'.toInt())
            for (b in bytes) {
                writer.write("\\x")
                writer.write(java.lang.Byte.toUnsignedInt(b).toString(16).padStart(2, '0'))
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