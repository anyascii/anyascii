package com.hunterwb.anyascii.build.gen

import com.hunterwb.anyascii.build.isAscii
import java.nio.file.Files
import java.nio.file.Path

fun js(g: Generator) {
    val dirPath = Path.of("js/data")
    dirPath.toFile().deleteRecursively()
    Files.createDirectories(dirPath)

    for ((blockNum, block) in g.blocks) {
        Files.newBufferedWriter(dirPath.resolve("%03x.js".format(blockNum))).use { w ->
            w.write("module.exports=[")
            for ((i, s) in block.withIndex()) {
                val cp = (blockNum shl 8) or i
                var a = s ?: ""
                if (cp.isAscii()) a = ""
                a = a.replace("\\", "\\\\").replace("'", "\\'")
                a = "'$a'"
                a += if (i == block.lastIndex) ']' else ','
                w.write(a)
            }
            w.write(";")
        }
    }

    Files.newBufferedWriter(Path.of("js/block.js")).use { writer ->
        writer.write("'use strict';\n\n")
        writer.write("module.exports = function block(blockNum) {\n")
        writer.write("    switch (blockNum) {\n")
        for (b in g.blocks.keys) {
            val hex = "%03x".format(b)
            writer.write("        case 0x$hex: return require('./data/$hex.js');\n")
        }
        writer.write("        default: return [];\n")
        writer.write("    }\n")
        writer.write("};\n")
    }
}