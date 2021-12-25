package com.anyascii.build.gen

import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.outputStream

fun java(g: Generator) {
    val resources = Path.of("impl/java/src/main/resources/com/anyascii")
    resources.toFile().deleteRecursively()
    resources.createDirectories()

    for ((blockNum, block) in g.blocks) {
        resources.resolve("%03x".format(blockNum)).outputStream().use { o ->
            for (s in block.values) {
                o.write(s.toByteArray())
                o.write(0xff)
            }
        }
    }
}