package com.anyascii.build.gen

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

fun julia(g: Generator) {
    val dir = Path.of("impl/julia/src/data")
    dir.toFile().deleteRecursively()
    Files.createDirectories(dir)

    for ((blockNum, block) in g.blocks) {
        Files.newOutputStream(dir.resolve("%03x".format(blockNum))).use { o ->
            for (s in block.values) {
                o.write(s.toByteArray(StandardCharsets.US_ASCII))
                o.write(0xff)
            }
        }
    }
}