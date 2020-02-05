package com.hunterwb.anyascii.build

import java.nio.file.Files
import java.nio.file.Path
import java.util.TreeSet

fun java(table: Table) {
    val dirPath = Path.of("java/src/main/resources/com/hunterwb/anyascii")
    dirPath.toFile().deleteRecursively()
    Files.createDirectories(dirPath)
    val blocks = TreeSet<Int>()
    for (b in 0..0xFFF) {
        val block = List(256) { table[(b shl 8) or it] }.dropLastWhile { it == null }
        if (block.isEmpty()) continue
        blocks.add(b)
        writeBlock(block, dirPath.resolve("%03x".format(b)))
    }
    writeSwitch(blocks, Path.of("java/src/main/java/com/hunterwb/anyascii/Block.java"))
}

private fun writeBlock(block: List<String?>, path: Path) {
    Files.newBufferedWriter(path).use { writer ->
        for (s in block) {
            if (s != null) writer.write(s)
            writer.write(0)
        }
    }
}

private fun writeSwitch(blocks: Set<Int>, path: Path) {
    Files.newBufferedWriter(path).use { writer ->
        writer.write("package com.hunterwb.anyascii;\n\n")
        writer.write("final class Block {\n\n")
        writer.write("\tstatic final String[][] blocks = new String[${blocks.size}][];\n\n")
        writer.write("\tstatic int block(int blockNum) {\n")
        writer.write("\t\tswitch (blockNum) {\n")
        for ((i, block) in blocks.withIndex()) {
            val s = "%03x".format(block)
            writer.write("\t\t\tcase 0x$s: return $i;\n")
        }
        writer.write("\t\t\tdefault: return -1;\n")
        writer.write("\t\t}\n")
        writer.write("\t}\n")
        writer.write("}\n")
    }
}