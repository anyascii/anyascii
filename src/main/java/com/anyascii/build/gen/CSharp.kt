package com.anyascii.build.gen

import java.nio.file.Path
import kotlin.io.path.bufferedWriter

fun cSharp(g: Generator) {
    Path.of("impl/csharp/src/Transliteration.data.cs").bufferedWriter().use { w ->
        w.write("namespace AnyAscii\n")
        w.write("{\n")
        w.write("\tpublic static partial class Transliteration\n")
        w.write("\t{\n\n")
        w.write("\t\tprivate static System.ReadOnlySpan<byte> Bank0 => new byte[]{")
        w.write(g.bank0.asSequence().joinToString(",") { it.code.toString() })
        w.write("};\n")
        w.write("\t\tprivate static System.ReadOnlySpan<byte> Bank1 => new byte[]{")
        w.write(g.bank1.asSequence().joinToString(",") { it.code.toString() })
        w.write("};\n\n")
        w.write("\t\tprivate static System.ReadOnlySpan<byte> Block(uint blockNum)\n")
        w.write("\t\t{\n")
        w.write("\t\t\tswitch (blockNum)\n")
        w.write("\t\t\t{\n")
        val bs = g.blockPointers.values.iterator()
        for (block in g.blocks.keys) {
            w.write("\t\t\t\tcase $block: return new byte[]{")
            w.write(bs.next().joinToString(",") { (it.toInt() and 0xff).toString() })
            w.write("};\n")
        }
        w.write("\t\t\t\tdefault: return new byte[]{};\n")
        w.write("\t\t\t}\n")
        w.write("\t\t}\n")
        w.write("\t}\n")
        w.write("}\n")
    }
}