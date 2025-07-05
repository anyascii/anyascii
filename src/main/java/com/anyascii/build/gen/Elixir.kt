package com.anyascii.build.gen

import com.anyascii.build.deflate
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.writeBytes

fun elixir(g: Generator) {
    val dirPath = Path.of("impl/elixir/priv")
    dirPath.toFile().deleteRecursively()
    dirPath.createDirectories()

    for ((blockNum, block) in g.blocks12) {
        val s = block.noAscii().joinToString("\t")
        dirPath.resolve("%02X".format(blockNum)).writeBytes(deflate(s))
    }
}
