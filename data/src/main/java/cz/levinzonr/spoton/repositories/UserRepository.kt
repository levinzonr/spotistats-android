package cz.levinzonr.spoton.repositories

import cz.levinzonr.spoton.models.UserResponse

interface UserRepository {

    suspend fun getCurrentUserProfile() : UserResponse
}