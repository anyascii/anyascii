package com.hunterwb.anyascii.build.gen

import java.nio.file.Files
import java.nio.file.Path

fun ruby(g: Generator) {
    val dirPath = Path.of("ruby/lib/data")
    dirPath.toFile().deleteRecursively()
    Files.createDirectories(dirPath)

    for ((blockNum, block) in g.blocks) {
        val s = "%03x".format(blockNum)
        Files.newBufferedWriter(dirPath.resolve("$s.rb".format(blockNum))).use { w ->
            w.write("module X$s\n")
            w.write("\tB=[")
            block.map { it.replace("\\", "\\\\").replace("'", "\\'") }
                    .map { "'$it'" }
                    .joinTo(w, ",")
            w.write("]\n")
            w.write("end\n")
        }
    }
}