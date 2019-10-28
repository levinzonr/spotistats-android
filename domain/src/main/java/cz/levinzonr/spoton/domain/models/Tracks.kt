package cz.levinzonr.spoton.domain.models

import cz.levinzonr.spoton.models.TrackResponse

data class Tracks(
        val tracksShort: List<TrackResponse> = listOf(),
        val tracksLong: List<TrackResponse> = listOf(),
        val tracksMid: List<TrackResponse> = listOf()
)