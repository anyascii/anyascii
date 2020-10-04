package com.anyascii.build.gen

import com.anyascii.build.deflate
import java.nio.file.Files
import java.nio.file.Path

fun python(g: Generator) {
    val dirPath = Path.of("python/anyascii/_data")
    dirPath.toFile().deleteRecursively()
    Files.createDirectories(dirPath)

    Files.createFile(dirPath.resolve("__init__.py"))

    for ((blockNum, block) in g.blocks) {
        Files.newOutputStream(dirPath.resolve("%03x".format(blockNum))).use { o ->
            o.write(deflate(block.noAscii().joinToString("\t").encodeToByteArray()))
        }
    }
}