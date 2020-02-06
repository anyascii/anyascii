package com.hunterwb.anyascii.build.gen

import com.hunterwb.anyascii.build.isAscii
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
            for ((i, s) in block.withIndex()) {
                val cp = (blockNum shl 8) or i
                var a = s ?: ""
                if (cp.isAscii()) a = ""
                a = a.replace("\\", "\\\\").replace("'", "\\'")
                a = "'$a'"
                a += if (i == block.lastIndex) ')' else ','
                w.write(a)
            }
        }
    }
}