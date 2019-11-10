package cz.levinzonr.spoton.models

data class AddTracksToPlaylistRequest(
        val uris: List<String>,
        val position: Int = 0
)