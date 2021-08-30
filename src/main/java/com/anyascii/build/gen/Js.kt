package com.anyascii.build.gen

import java.nio.file.Files
import java.nio.file.Path

fun js(g: Generator) {
    Files.newBufferedWriter(Path.of("impl/js/block.js")).use { w ->
        w.write("function block(blockNum) {\n")
        w.write("switch (blockNum) {\n")
        for ((blockNum, block) in g.blocks) {
            val s = block.noAscii().joinToString("\t").replace("\\", "\\\\").replace("\"", "\\\"")
            w.write("case $blockNum:return\"$s\"\n")
        }
        w.write("}\n")
        w.write("return\"\"\n")
        w.write("}\n")
        w.write("exports.default = block;\n")
    }
}