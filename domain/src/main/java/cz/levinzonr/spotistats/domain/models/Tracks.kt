package cz.levinzonr.spotistats.domain.models

import cz.levinzonr.spotistats.models.TrackResponse

data class Tracks(
        val tracksShort: List<TrackResponse> = listOf(),
        val tracksLong: List<TrackResponse> = listOf(),
        val tracksMid: List<TrackResponse> = listOf()
)