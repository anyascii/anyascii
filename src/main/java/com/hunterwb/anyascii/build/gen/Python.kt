package com.hunterwb.anyascii.build.gen

import java.nio.file.Files
import java.nio.file.Path

fun python(g: Generator) {
    val dirPath = Path.of("python/anyascii/_data")
    dirPath.toFile().deleteRecursively()
    Files.createDirectories(dirPath)

    Files.createFile(dirPath.resolve("__init__.py"))

    for ((blockNum, block) in g.blocks) {
        Files.newBufferedWriter(dirPath.resolve("_%03x.py".format(blockNum))).use { w ->
            w.write("b=(")
            block.map { it.replace("\\", "\\\\").replace("'", "\\'") }
                    .map { "'$it'" }
                    .joinTo(w, ",")
            w.write(")")
        }
    }
}