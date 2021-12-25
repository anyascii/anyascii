package com.anyascii.build.gen

import java.nio.file.Path
import kotlin.io.path.bufferedWriter

fun js(g: Generator) {
    Path.of("impl/js/block.js").bufferedWriter().use { w ->
        w.write("export default function block(blockNum) {\n")
        w.write("switch (blockNum) {\n")
        for ((blockNum, block) in g.blocks) {
            val s = block.noAscii().joinToString("\t").replace("\\", "\\\\").replace("\"", "\\\"")
            w.write("case $blockNum:return\"$s\"\n")
        }
        w.write("}\n")
        w.write("return\"\"\n")
        w.write("}\n")
    }
}