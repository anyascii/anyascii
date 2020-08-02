package com.anyascii.build.gen

import java.nio.file.Files
import java.nio.file.Path

fun ruby(g: Generator) {
    val dirPath = Path.of("ruby/lib/data")
    dirPath.toFile().deleteRecursively()
    Files.createDirectories(dirPath)

    for ((blockNum, block) in g.blocks) {
        val b = "%03x".format(blockNum)
        Files.newBufferedWriter(dirPath.resolve("$b.rb")).use { w ->
            val s = block.joinToString("\t").replace("\\", "\\\\").replace("'", "\\'")
            w.write("module X$b B='$s'.split '\t' end")
        }
    }
}