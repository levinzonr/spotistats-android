package cz.levinzonr.spotistats.models

data class TrackResponse(
    val album: Album,
    val artists: List<Artist>,
    val available_markets: List<String>,
    val disc_number: Int,
    val duration_ms: Long,
    val explicit: Boolean,
    val external_ids: Map<String, String>,
    val external_urls: Map<String, String>,
    val href: String,
    val id: String,
    val is_local: Boolean,
    val name: String,
    val popularity: Int,
    val preview_url: String,
    val track_number: Int,
    val type: String,
    val uri: String
)


fun TrackResponse.artists() : String {
    return artists.joinToString(", "){ it.name }
}