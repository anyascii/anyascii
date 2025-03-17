package com.anyascii.build

fun superstring(strings: Collection<String>): String {
    val ss = removeSubstrings(strings).sortedByDescending { it.length }.toCollection(LinkedHashSet())
    var result = ""
    while (ss.isNotEmpty()) {
        val m = ss.maxByOrNull { overlap(result, it) }!!
        ss.remove(m)
        result += m.substring(overlap(result, m))
    }
    return result
}

private fun removeSubstrings(strings: Collection<String>): Set<String> {
    val ss = LinkedHashSet(strings)
    ss.removeIf { s1 -> ss.any { s2 -> s1 != s2 && s1 in s2 } }
    return ss
}

private fun overlap(a: String, b: String): Int {
    for (n in (b.length - 1).downTo(1)) {
        if (a.regionMatches(a.length - n, b, 0, n)) return n
    }
    return 0
}
