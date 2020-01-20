package com.hunterwb.anyascii.build

import java.nio.file.Files
import java.nio.file.Path

fun js(table: Table) {
    val dirPath = Path.of("js/data")
    dirPath.toFile().deleteRecursively()
    Files.createDirectories(dirPath)
    val bs = HashSet<Int>()
    for (b in 0..0xFFF) {
        val block = List(256) { table[(b shl 8) or it] }.dropLastWhile { it == null }
        if (block.isEmpty()) continue
        bs.add(b)
        writeBlock(block, dirPath.resolve("%03x.js".format(b)))
    }
    writeSwitch(bs, Path.of("js/block.js"))
}

private fun writeBlock(block: List<String?>, path: Path) {
    Files.newBufferedWriter(path).use { writer ->
        writer.write("module.exports=[")
        for (c in block.indices) {
            var s = block[c] ?: ""
            s = s.replace("\\", "\\\\").replace("'", "\\'")
            s = "'$s'"
            s += if (c == block.lastIndex) ']' else ','
            writer.write(s)
        }
        writer.write(";")
    }
}

private fun writeSwitch(blocks: Set<Int>, path: Path) {
    Files.newBufferedWriter(path).use { writer ->
        writer.write("'use strict';\n\n")
        writer.write("module.exports = function block(blockNum) {\n")
        writer.write("    switch (blockNum) {\n")
        for (b in blocks) {
            val hex = "%03x".format(b)
            writer.write("        case 0x$hex: return require('./data/$hex.js');\n")
        }
        writer.write("        default: return [];\n")
        writer.write("    }\n")
        writer.write("};\n")
    }
}