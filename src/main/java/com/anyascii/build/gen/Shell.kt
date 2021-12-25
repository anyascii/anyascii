package com.anyascii.build.gen

import com.anyascii.build.javaClass
import java.nio.file.Path
import kotlin.io.path.bufferedWriter
import kotlin.io.path.readLines

fun shell(g: Generator) {
    Path.of("impl/sh/anyascii").bufferedWriter().use { w ->
        w.write("#!/bin/sh\n")
        val license = Path.of("LICENSE").readLines().joinToString("") { "# $it\n" }
        w.write("\n$license\n")
        for ((blockNum, block) in g.blocks) {
            w.write("_$blockNum='")
            w.write(block.noAscii().joinToString("\t").replace("'", "'\\''"))
            w.write("'\n")
        }
        w.write("\n")
        w.write(javaClass.getResource("body.sh")!!.readText())
    }
}