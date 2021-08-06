package com.anyascii.build.gen

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

fun java(g: Generator) {
    val resources = Path.of("impl/java/src/main/resources/com/anyascii")
    resources.toFile().deleteRecursively()
    Files.createDirectories(resources)

    for ((blockNum, block) in g.blocks) {
        Files.newOutputStream(resources.resolve("%03x".format(blockNum))).use { o ->
            for (s in block.values) {
                o.write(s.toByteArray(StandardCharsets.US_ASCII))
                o.write(0xff)
            }
        }
    }
}