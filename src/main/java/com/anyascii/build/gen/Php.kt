package com.anyascii.build.gen

import java.nio.file.Files
import java.nio.file.Path

fun php(g: Generator) {
    val dirPath = Path.of("php/_data")
    dirPath.toFile().deleteRecursively()
    Files.createDirectories(dirPath)

    for ((blockNum, block) in g.blocks) {
        Files.newBufferedWriter(dirPath.resolve("_%03x.php".format(blockNum))).use { w ->
            val s = block.joinToString("\t").replace("\\", "\\\\").replace("'", "\\'")
            w.write("<?php return explode('\t','$s');")
        }
    }
}