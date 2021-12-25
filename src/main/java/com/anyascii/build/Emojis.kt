package com.anyascii.build

import com.fasterxml.jackson.databind.ObjectMapper
import com.ibm.icu.lang.UCharacter.UnicodeBlock.*
import com.ibm.icu.lang.UProperty.*
import java.io.File

fun emojis() = Table()
        .then(discordEmojis())
        .then(fallbackEmojis())
        .remove(BLACKLIST)

private fun discordEmojis() = ObjectMapper()
        .readTree(File("input/discord-emojis.json"))
        .flatten()
        .filter { it["surrogates"].asText().codePointsArray().dropLastWhile { it == 0xfe0f }.size == 1 }
        .associateTo(Table()) { it["surrogates"].asText().codePointAt(0) to it["names"].first().asText().let { ":$it:" } }

private fun fallbackEmojis() = EMOJIS.toTable { ':' + it.name.lower().replace(' ', '_').replace('-', '_') + ':' }

private val BLACKLIST =
        block(LATIN_1_SUPPLEMENT) +
        block(ENCLOSED_ALPHANUMERICS) +
        block(GENERAL_PUNCTUATION) +
        block(LETTERLIKE_SYMBOLS) +
        block(ENCLOSED_CJK_LETTERS_AND_MONTHS) +
        block(ENCLOSED_ALPHANUMERIC_SUPPLEMENT) +
        block(ENCLOSED_IDEOGRAPHIC_SUPPLEMENT)

private val EMOJIS = property(EMOJI_PRESENTATION) - property(EMOJI_COMPONENT)
