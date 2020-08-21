package com.anyascii.build.gen

import java.nio.file.Files
import java.nio.file.Path

fun python(g: Generator) {
    val dirPath = Path.of("python/anyascii/_data")
    dirPath.toFile().deleteRecursively()
    Files.createDirectories(dirPath)

    Files.createFile(dirPath.resolve("__init__.py"))

    for ((blockNum, block) in g.blocks) {
        Files.newBufferedWriter(dirPath.resolve("_%03x.py".format(blockNum))).use { w ->
            var s = block.noAscii().joinToString("\t").replace("\\", "\\\\")
            s = if (s.count { it == '\'' } > s.count { it == '"' }) {
                '"' + s.replace("\"", "\\\"") + '"'
            } else {
                '\'' + s.replace("'", "\\'") + '\''
            }
            w.write("b=$s")
        }
    }
}