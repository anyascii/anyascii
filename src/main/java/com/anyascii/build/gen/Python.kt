package com.anyascii.build.gen

import com.anyascii.build.deflate
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.writeBytes

fun python(g: Generator) {
    val dirPath = Path.of("impl/python/anyascii/_data")
    dirPath.toFile().deleteRecursively()
    dirPath.createDirectories()

    dirPath.resolve("__init__.py").createFile()

    for ((blockNum, block) in g.blocks12) {
        val s = block.noAscii().joinToString("\t")
        dirPath.resolve("%02x".format(blockNum)).writeBytes(deflate(s))
    }
}