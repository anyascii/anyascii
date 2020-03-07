package com.hunterwb.anyascii.build

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

private val EMOJI_BLACKLIST = "©®‼⁉™ℹⓂ㊗㊙🈁🈂🈚🈯🈲🈳🈴🈵🈶🈷🈸🈹🈺🉐🉑".codePointsArray().asList()

fun emojis() = discordEmojis()

private fun gitHubEmojis(): Table = jacksonObjectMapper()
        .readValue<LinkedHashMap<String, String>>(File("input/github.emojis.json"))
        .filterValues { it.contains("/unicode/") && '-' !in it }
        .mapKeys { ":${it.key}:" }
        .mapValues { it.value.substringBeforeLast('.').substringAfterLast("/").toInt(16) }
        .entries
        .associateTo(Table()) { it.value to it.key }
        .minus(EMOJI_BLACKLIST)

private fun discordEmojis() = jacksonObjectMapper()
        .readTree(File("input/discord.emojis.json"))
        .flatMap { it }
        .filter { it["surrogates"].asText().codePointsArray().dropLastWhile { it == 0xfe0f }.size == 1 }
        .associateTo(Table()) { it["surrogates"].asText().codePointAt(0) to it["names"].first().asText().let { ":$it:" } }
        .minus(EMOJI_BLACKLIST)