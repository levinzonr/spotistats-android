package cz.levinzonr.spoton.models

data class Track(
    val album: Album,
    val artists: List<Artist>,
    val available_markets: List<String>,
    val disc_number: Int,
    val duration_ms: Int,
    val explicit: Boolean,
    val external_ids: Map<String, String>,
    val external_urls: Map<String, String>,
    val href: String,
    val id: String,
    val name: String,
    val popularity: Int,
    val preview_url: String,
    val track_number: Int,
    val type: String,
    val uri: String
)