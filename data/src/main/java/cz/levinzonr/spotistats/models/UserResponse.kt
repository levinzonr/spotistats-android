package cz.levinzonr.spotistats.models

data class UserResponse(
    val country: String,
    val display_name: String,
    val email: String,
    val external_urls: Map<String, String>,
    val followers: Followers,
    val href: String,
    val id: String,
    val images: List<Image>,
    val product: String,
    val type: String,
    val uri: String
)