package cz.levinzonr.spotistats.repositories

import cz.levinzonr.spotistats.models.UserResponse

interface UserRepository {

    suspend fun getCurrentUserProfile() : UserResponse
}