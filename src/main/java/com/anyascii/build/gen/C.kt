package com.anyascii.build.gen

import com.anyascii.build.javaClass
import java.nio.file.Path
import kotlin.io.path.bufferedWriter
import kotlin.io.path.readText

fun c(g: Generator) {
    Path.of("impl/c/anyascii.c").bufferedWriter().use { w ->
        w.write("/*\n")
        w.write(Path.of("LICENSE").readText())
        w.write("*/\n\n")
        w.write("#include <stddef.h>\n")
        w.write("#include <stdint.h>\n")
        w.write("#include \"anyascii.h\"\n\n")
        w.write("static const char bank[${g.stringsBank.length}] = \"")
        w.write(g.stringsBank.replace("\\", "\\\\").replace("\"", "\\\""))
        w.write("\";\n\n")

        val bs = g.blockPointers.values.iterator()
        for (block in g.blocks.keys) {
            val s = "b%03x".format(block)
            val bp = bs.next()
            w.write("static const char $s[${bp.size + 1}] = \"")
            w.write(BYTE_STRINGS[(bp.size / 3) - 1])
            for (b in bp) {
                w.write(BYTE_STRINGS[b.toInt() and 0xff])
            }
            w.write("\";\n")
        }
        w.write("static const char bdefault[4] = \"\\000\\000\\000\\200\";\n\n")

        w.write("static const char *block(uint_least32_t blocknum) {\n")
        w.write("\tswitch (blocknum) {\n")
        for (block in g.blocks.keys) {
            val s = "%03x".format(block)
            w.write("\t\tcase 0x$s: return b$s;\n")
        }
        w.write("\t\tdefault: return bdefault;\n")
        w.write("\t}\n")
        w.write("}\n\n")

        w.write(javaClass.getResource("body.c")!!.readText())
    }
}

private val BYTE_STRINGS = Array(256) {
    val c = it.toChar()
    if (it !in 0x20..0x7e || c == '\\' || c == '"') {
        "\\%03o".format(it)
    } else {
        c.toString()
    }
}