package cz.levinzonr.spotistats.models

data class SpotifyCredentials(
        val clientId: String,
        val clientSecret: String,
        val redirectUri: String
)