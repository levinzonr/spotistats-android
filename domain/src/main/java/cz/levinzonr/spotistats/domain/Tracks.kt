package cz.levinzonr.spotistats.domain

import cz.levinzonr.spotistats.models.Item

data class Tracks(
        val tracksShort: List<Item> = listOf(),
        val tracksLong: List<Item> = listOf(),
        val tracksMid: List<Item> = listOf()
)