package cz.levinzonr.spoton.models.musicxmatch

data class LyrcisResponse(
    val action_requested: String,
    val can_edit: Int,
    val explicit: Int,
    val html_tracking_url: String,
    val instrumental: Int,
    val locked: Int,
    val lyrics_body: String,
    val lyrics_copyright: String,
    val lyrics_id: Int,
    val lyrics_language: String,
    val lyrics_language_description: String,
    val pixel_tracking_url: String,
    val publisher_list: List<String>,
    val restricted: Int,
    val script_tracking_url: String,
    val updated_time: String,
    val verified: Int,
    val writer_list: List<String>
)