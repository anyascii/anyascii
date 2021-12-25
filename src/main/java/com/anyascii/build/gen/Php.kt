package com.anyascii.build.gen

import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.writeText

fun php(g: Generator) {
    val dirPath = Path.of("impl/php/_data")
    dirPath.toFile().deleteRecursively()
    dirPath.createDirectories()

    for ((blockNum, block) in g.blocks) {
        val b = block.noAscii().joinToString("\t").replace("\\", "\\\\").replace("'", "\\'")
        val s = "<?php return explode('\t','$b');"
        dirPath.resolve("_%03x.php".format(blockNum)).writeText(s)
    }
}