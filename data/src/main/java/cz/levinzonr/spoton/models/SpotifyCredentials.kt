package cz.levinzonr.spoton.models

data class SpotifyCredentials(
        val clientId: String,
        val clientSecret: String,
        val redirectUri: String
)