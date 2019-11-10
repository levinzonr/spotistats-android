package cz.levinzonr.spoton.models

data class CreatePlaylistRequest(
        val name: String,
        val description: String,
        val public: Boolean = true,
        val collaborative: Boolean = false
)