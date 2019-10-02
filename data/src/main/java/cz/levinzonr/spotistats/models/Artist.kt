package cz.levinzonr.spotistats.models

data class Artist(
    val external_urls: Map<String, String>,
    val href: String,
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)