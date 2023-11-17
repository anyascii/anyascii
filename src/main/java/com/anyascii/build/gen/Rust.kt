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

    Path.of("impl/rust/src/bank1.txt").writeText(g.bank1)
    Path.of("impl/rust/src/bank2.txt").writeText(g.bank2)

    for ((blockNum, bytes) in g.blockPointers) {
        dataDir.resolve("%03x".format(blockNum)).writeBytes(bytes)
    }

    Path.of("impl/rust/src/data.rs").bufferedWriter().use { writer ->
        writer.write("pub const BANK1: &str = include_str!(\"bank1.txt\");\n")
        writer.write("pub const BANK2: &str = include_str!(\"bank2.txt\");\n")
        writer.write("pub const BANK2_LENGTH: usize = $BANK2_LENGTH;\n\n")

        writer.write("pub fn block(block_num: u32) -> &'static [[u8; 3]] {\n")
        writer.write("    let b: &'static [u8] = match block_num {\n")
        for (block in g.blocks.keys) {
            val s = "%03x".format(block)
            writer.write("        0x$s => include_bytes!(\"data/$s\"),\n")
        }
        writer.write("        _ => &[],\n")
        writer.write("    };\n")
        writer.write("    unsafe { core::slice::from_raw_parts(b.as_ptr().cast(), b.len() / 3) }\n")
        writer.write("}\n")
    }
}