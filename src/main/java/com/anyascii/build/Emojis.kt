package com.anyascii.build

import com.fasterxml.jackson.databind.ObjectMapper
import com.ibm.icu.lang.UCharacter.UnicodeBlock
import com.ibm.icu.lang.UProperty
import java.io.File

fun emojis() = Table()
        .then(discordEmojis())
        .then(fallbackEmojis())

private fun discordEmojis() = ObjectMapper()
        .readTree(File("input/discord-emojis.json"))
        .flatten()
        .filter { it["surrogates"].asText().codePointsArray().dropLastWhile { it == 0xfe0f }.size == 1 }
        .associateTo(Table()) { it["surrogates"].asText().codePointAt(0) to it["names"].first().asText().let { ":$it:" } }
        .filterTo(Table()) { it.key.block !in BLACKLIST }

private fun fallbackEmojis() = ALL_CODE_POINTS.filter { it.hasBinaryProperty(UProperty.EMOJI_PRESENTATION) && !it.hasBinaryProperty(UProperty.EMOJI_COMPONENT) && it.block !in BLACKLIST }
        .toTable { ':' + it.name.lower().replace(' ', '_').replace('-', '_') + ':' }

private val BLACKLIST = setOf(
        UnicodeBlock.LATIN_1_SUPPLEMENT,
        UnicodeBlock.ENCLOSED_ALPHANUMERICS,
        UnicodeBlock.GENERAL_PUNCTUATION,
        UnicodeBlock.LETTERLIKE_SYMBOLS,
        UnicodeBlock.ENCLOSED_CJK_LETTERS_AND_MONTHS,
        UnicodeBlock.ENCLOSED_ALPHANUMERIC_SUPPLEMENT,
        UnicodeBlock.ENCLOSED_IDEOGRAPHIC_SUPPLEMENT,
)