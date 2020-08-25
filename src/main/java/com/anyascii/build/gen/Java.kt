package com.anyascii.build.gen

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

fun java(g: Generator) {
    val resources = Path.of("java/src/main/resources/com/anyascii")
    resources.toFile().deleteRecursively()
    Files.createDirectories(resources)

    for ((blockNum, block) in g.blocks) {
        Files.newOutputStream(resources.resolve("%03x".format(blockNum))).use { o ->
            for (s in block.values) {
                o.write(s.toByteArray(StandardCharsets.US_ASCII))
                o.write(0xff)
            }
        }
    }

    Files.newBufferedWriter(Path.of("java/src/main/java/com/anyascii/Block.java")).use { w ->
        w.write("package com.anyascii;\n\n")
        w.write("final class Block {\n\n")
        w.write("\tstatic final String[][] blocks = new String[${g.blocks.size}][];\n\n")
        w.write("\tstatic int block(int blockNum) {\n")
        w.write("\t\tswitch (blockNum) {\n")
        for ((i, block) in g.blocks.keys.withIndex()) {
            val s = "%03x".format(block)
            w.write("\t\t\tcase 0x$s: return $i;\n")
        }
        w.write("\t\t\tdefault: return -1;\n")
        w.write("\t\t}\n")
        w.write("\t}\n")
        w.write("}\n")
    }
}