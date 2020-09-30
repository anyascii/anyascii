package com.anyascii.build.gen

import java.io.ByteArrayOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.Deflater
import java.util.zip.DeflaterOutputStream

fun python(g: Generator) {
    val dirPath = Path.of("python/anyascii/_data")
    dirPath.toFile().deleteRecursively()
    Files.createDirectories(dirPath)

    Files.createFile(dirPath.resolve("__init__.py"))

    for ((blockNum, block) in g.blocks) {
        Files.newOutputStream(dirPath.resolve("%03x".format(blockNum))).use { o ->
            o.write(compress(block.noAscii().joinToString("\t").encodeToByteArray()))
        }
    }
}

private fun compress(b: ByteArray): ByteArray {
    val o = ByteArrayOutputStream()
    val d = Deflater(Deflater.BEST_COMPRESSION)
    DeflaterOutputStream(o, d).use { s ->
        b.inputStream().transferTo(s)
    }
    d.end()
    return o.toByteArray()
}