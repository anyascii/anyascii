package com.anyascii.build.gen

import java.nio.file.Files
import java.nio.file.Path

fun rust(g: Generator) {
    val dataDir = Path.of("impl/rust/src/data")
    dataDir.toFile().deleteRecursively()
    Files.createDirectories(dataDir)

    Files.writeString(Path.of("impl/rust/src/bank.txt"), g.stringsBank)

    for ((blockNum, bytes) in g.blockPointers) {
        val f = dataDir.resolve("%03x.bin".format(blockNum))
        Files.write(f, bytes)
    }

    Files.newBufferedWriter(Path.of("impl/rust/src/block.rs")).use { writer ->
        writer.write("pub fn block(block_num: u16) -> &'static [u8] {\n")
        writer.write("\tmatch block_num {\n")
        for (block in g.blocks.keys) {
            val s = "%03x".format(block)
            writer.write("\t\t0x$s => include_bytes!(\"data/$s.bin\"),\n")
        }
        writer.write("\t\t_ => &[]\n")
        writer.write("\t}\n")
        writer.write("}\n")
    }
}