package com.hunterwb.anyascii.build.gen

import java.nio.file.Files
import java.nio.file.Path

fun js(g: Generator) {
    val dirPath = Path.of("js/data")
    dirPath.toFile().deleteRecursively()
    Files.createDirectories(dirPath)

    for ((blockNum, block) in g.blocks) {
        Files.newBufferedWriter(dirPath.resolve("%03x.js".format(blockNum))).use { w ->
            w.write("module.exports=[")
            block.map { it.replace("\\", "\\\\").replace("'", "\\'") }
                    .map { "'$it'" }
                    .joinTo(w, ",")
            w.write("];")
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