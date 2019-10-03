package cz.levinzonr.spotistats.models

data class RecommendedTracks(
    val seeds: List<Seed>,
    val tracks: List<TrackResponse>
)