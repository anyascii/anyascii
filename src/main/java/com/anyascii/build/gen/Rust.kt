package com.anyascii.build.gen

import java.nio.file.Path
import kotlin.io.path.bufferedWriter
import kotlin.io.path.createDirectories
import kotlin.io.path.writeBytes
import kotlin.io.path.writeText

fun rust(g: Generator) {
    val dataDir = Path.of("impl/rust/src/data")
    dataDir.toFile().deleteRecursively()
    dataDir.createDirectories()

    Path.of("impl/rust/src/bank.txt").writeText(g.stringsBank)

    for ((blockNum, bytes) in g.blockPointers) {
        dataDir.resolve("%03x".format(blockNum)).writeBytes(bytes)
    }

    Path.of("impl/rust/src/block.rs").bufferedWriter().use { writer ->
        writer.write("pub fn block(block_num: u32) -> &'static [[u8; 3]] {\n")
        writer.write("\tlet b: &'static [u8] = match block_num {\n")
        for (block in g.blocks.keys) {
            val s = "%03x".format(block)
            writer.write("\t\t0x$s => include_bytes!(\"data/$s\"),\n")
        }
        writer.write("\t\t_ => &[]\n")
        writer.write("\t};\n")
        writer.write("\tunsafe { core::slice::from_raw_parts(b.as_ptr().cast(), b.len() / 3) }\n")
        writer.write("}\n")
    }
}