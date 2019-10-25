package cz.levinzonr.spotistats.models

data class Tracks(
        val items: List<PlaylistTrack>
)


data class PlaylistTrack(val track: Track)