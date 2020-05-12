package com.hunterwb.anyascii.build.gen

import java.lang.invoke.MethodHandles
import java.nio.file.Files
import java.nio.file.Path

fun shell(g: Generator) {
    Files.newBufferedWriter(Path.of("sh/anyascii")).use { w ->
        w.write("#!/bin/sh\n\n")
        for ((blockNum, block) in g.blocks) {
            w.write("_%03x='".format(blockNum))
            w.write(block.joinToString("\t").replace("'", "'\\''"))
            w.write("'\n")
        }
        w.write("\n")
        w.write(MethodHandles.lookup().lookupClass().getResource("body.sh").readText())
    }
}