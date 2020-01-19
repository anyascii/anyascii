package com.hunterwb.anyascii.build

import java.nio.file.Files
import java.nio.file.Path

fun java(table: Table) {
    val dirPath = Path.of("java/src/main/resources/com/hunterwb/anyascii")
    dirPath.toFile().deleteRecursively()
    Files.createDirectories(dirPath)
    for (b in 0..0x2FF) {
        val block = List(256) { table[(b shl 8) or it] }.dropLastWhile { it == null }
        if (block.isEmpty()) continue
        writeBlock(block, dirPath.resolve("%03x".format(b)))
    }
}

private fun writeBlock(block: List<String?>, path: Path) {
    Files.newBufferedWriter(path).use { writer ->
        for (s in block) {
            if (s != null) writer.write(s)
            writer.write(0)
        }
    }
}