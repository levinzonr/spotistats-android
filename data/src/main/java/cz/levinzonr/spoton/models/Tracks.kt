package cz.levinzonr.spoton.models

data class Tracks(
        val items: List<PlaylistTrack>
)


data class PlaylistTrack(val track: Track)