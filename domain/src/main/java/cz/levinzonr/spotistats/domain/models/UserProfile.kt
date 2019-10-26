package cz.levinzonr.spotistats.domain.models

data class UserProfile(
        val displayName: String,
        val imageUrl: String?,
        val followersCount: Int,
        val playlistCount: Int
)