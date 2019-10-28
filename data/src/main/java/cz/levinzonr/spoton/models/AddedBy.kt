package cz.levinzonr.spoton.models

data class AddedBy(
    val external_urls: Map<String, String>,
    val href: String,
    val id: String,
    val type: String,
    val uri: String
)