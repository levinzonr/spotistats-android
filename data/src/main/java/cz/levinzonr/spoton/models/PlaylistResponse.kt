package cz.levinzonr.spoton.models

data class PlaylistResponse(
        val collaborative: Boolean,
        val description: String,
        val external_urls: Map<String, String>,
        val followers: Followers,
        val href: String,
        val id: String,
        val images: List<Image>,
        val name: String,
        val owner: Owner,
        val snapshot_id: String,
        val tracks: Tracks,
        val type: String,
        val uri: String
)


data class PlaylistItem(val tracks: Track)