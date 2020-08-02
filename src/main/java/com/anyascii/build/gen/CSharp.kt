package com.anyascii.build.gen

import java.nio.file.Files
import java.nio.file.Path

fun cSharp(g: Generator) {
    Files.newBufferedWriter(Path.of("csharp/src/Transliteration.blocks.cs")).use { w ->
        w.write("using System.Collections.Generic;\n")
        w.write("using System;\n\n")
        w.write("namespace AnyAscii\n")
        w.write("{\n")
        w.write("\tpublic static partial class Transliteration\n")
        w.write("\t{\n")
        w.write("\t\tprivate static readonly Dictionary<int, Lazy<string[]>> blocks = new Dictionary<int, Lazy<string[]>>\n")
        w.write("\t\t{\n")

        for ((blockNum, block) in g.blocks) {
            val h = "%03x".format(blockNum)
            w.write("\t\t\t{ 0x$h, new Lazy<string[]>(() => new[] { ${block.joinToString { escape(it) } } }) },\n")
        }

        w.write("\t\t};\n")
        w.write("\t}\n")
        w.write("}\n")
    }
}

private fun escape(s: String) = '"' + s.replace("\\", "\\\\").replace("\"", "\\\"") + '"'