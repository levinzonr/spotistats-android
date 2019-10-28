package cz.levinzonr.spoton.models

data class RecommendedTracks(
    val seeds: List<Seed>,
    val tracks: List<TrackResponse>
)