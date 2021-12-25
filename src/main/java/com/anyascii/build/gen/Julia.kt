package com.anyascii.build.gen

import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.outputStream

fun julia(g: Generator) {
    val dir = Path.of("impl/julia/src/data")
    dir.toFile().deleteRecursively()
    dir.createDirectories()

    for ((blockNum, block) in g.blocks) {
        dir.resolve("%03x".format(blockNum)).outputStream().use { o ->
            for (s in block.values) {
                o.write(s.toByteArray())
                o.write(0xff)
            }
        }
    }
}