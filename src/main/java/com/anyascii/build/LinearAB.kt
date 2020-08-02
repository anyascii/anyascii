package com.anyascii.build

// http://www.people.ku.edu/~jyounger/LinearA/LinAIdeograms/

fun linearAB() = linearBSyllables()
        .then(catalogueIds())

private fun linearBSyllables() = (0x10000..0x1003f).filterDefined().toTable { it.name.substringAfterLast(' ').lower() }

private val CATALOGUE_ID = "([AB]{1,2})(\\d{3})(\\S*)".toRegex()

private fun catalogueIds() = codePoints("Lina").plus(codePoints("Linb")).toTable { catalogueId(it) }

private fun catalogueId(cp: CodePoint): String {
    val (prefix, num, suffix) = CATALOGUE_ID.findOnly(cp.name).destructured
    return prefix + num.stripLeading('0') + suffix.removeSuffix("-VAS").replace("-102", "/102").lower()
}