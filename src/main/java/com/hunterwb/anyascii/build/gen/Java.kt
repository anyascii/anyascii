package com.hunterwb.anyascii.build.gen

import java.nio.file.Files
import java.nio.file.Path

fun java(g: Generator) {
    val resources = Path.of("java/src/main/resources/com/hunterwb/anyascii")
    resources.toFile().deleteRecursively()
    Files.createDirectories(resources)

    for ((blockNum, block) in g.blocks) {
        Files.newBufferedWriter(resources.resolve("%03x".format(blockNum))).use { w ->
            for (s in block) {
                w.write(s)
                w.write(0)
            }
        }
    }

    Files.newBufferedWriter(Path.of("java/src/main/java/com/hunterwb/anyascii/Block.java")).use { w ->
        w.write("package com.hunterwb.anyascii;\n\n")
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