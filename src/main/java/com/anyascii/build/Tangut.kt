package com.anyascii.build

import com.ibm.icu.lang.UCharacter.UnicodeBlock
import java.nio.file.Path

fun tangut() = characters().then(components())

private fun characters(): Table {
    val lfwToCp = HashMap<Int, CodePoint>()
    // https://babelstone.co.uk/Tangut
    forEachLine(Path.of("input/tangut-lfw-unicode.csv")) { line ->
        val split = line.split(',')
        lfwToCp[split[0].toInt()] = split[1].toInt(16)
    }

    val chars = Table()
    // http://amritas.com/150207.htm#02012357
    forEachLine(Path.of("input/tangutdb-1-0.csv")) { line ->
        val split = line.split(',')
        val lfw = split[0].removePrefix("L").stripLeading('0').toIntOrNull() ?: return@forEachLine
        chars[lfwToCp.getValue(lfw)] = split[1]
    }

    return chars
}

private fun components() = UnicodeBlock.TANGUT_COMPONENTS.toTable { it.name.substringAfterLast('-').stripLeading('0') }