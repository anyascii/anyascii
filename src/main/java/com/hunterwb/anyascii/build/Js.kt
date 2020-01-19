package com.hunterwb.anyascii.build

import java.nio.file.Files
import java.nio.file.Path

fun js(table: Table) {
    val dirPath = Path.of("js/data")
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
        writer.write("""
            'use strict';

            const blocks = {};

            module.exports = function block(blockNum) {
                let b = blocks[blockNum];
                if (b !== undefined) return b;
                switch (blockNum) {
        """.trimIndent())
        writer.write("\n")
        for (b in blocks) {
            val hex = "%03x".format(b)
            writer.write("        case 0x$hex:\n")
            writer.write("            b = require('./data/$hex.js');\n")
            writer.write("            break;\n")
        }
        writer.write("""
                    default:
                        b = [];
                }
                blocks[blockNum] = b;
                return b;
            }
        """.trimIndent())
    }
}