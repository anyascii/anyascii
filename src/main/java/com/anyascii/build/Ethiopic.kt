package com.anyascii.build

import java.nio.file.Files
import java.nio.file.Path

fun ethiopic() = Table("ethiopic").then(letters())

//bgn/pcgn tigrinya 2007
//ungegn amharic 1967
//http://yacob.org/papers/DanielYacob-IUC25.pdf
//https://unicode.org/wg2/docs/n3572.pdf
//https://unicode.org/L2/L2021/21037-gurage-adds.pdf
//http://keyboards.ethiopic.org/specification/
//https://unicode.org/wg2/docs/n2814.pdf

private fun letters() = Table().apply {
    Files.newBufferedReader(Path.of("input/ethiopic.csv")).use { r ->
        val vowels = r.readLine().split(',')
        while (true) {
            val line = r.readLine()?.split(',') ?: break
            val consonant = line[0]
            for (i in 1 until line.size) {
                val col = line[i]
                if (col.isEmpty()) continue
                this[col.toCodePoint()] = consonant + vowels[i]
            }
        }
    }
}
