package com.anyascii.build

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File

private val EMOJI_BLACKLIST = "©®‼⁉™Ⓜ㊗㊙🈁🈂🈚🈯🈲🈳🈴🈵🈶🈷🈸🈹🈺🉐🉑".codePointsArray().asList()

fun emojis() = ObjectMapper()
        .readTree(File("input/discord-emojis.json"))
        .flatten()
        .filter { it["surrogates"].asText().codePointsArray().dropLastWhile { it == 0xfe0f }.size == 1 }
        .associateTo(Table()) { it["surrogates"].asText().codePointAt(0) to it["names"].first().asText().let { ":$it:" } }
        .minus(EMOJI_BLACKLIST)