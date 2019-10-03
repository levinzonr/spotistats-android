package cz.levinzonr.spotistats.domain

import cz.levinzonr.spotistats.models.TrackResponse

data class Tracks(
        val tracksShort: List<TrackResponse> = listOf(),
        val tracksLong: List<TrackResponse> = listOf(),
        val tracksMid: List<TrackResponse> = listOf()
)