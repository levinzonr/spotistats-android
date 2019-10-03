package cz.levinzonr.spotistats.network

import cz.levinzonr.spotistats.models.UserResponse
import cz.levinzonr.spotistats.repositories.UserRepository

class RestUserRepository(private val dataSource: Api) : UserRepository {

    override suspend fun getCurrentUserProfile(): UserResponse {
        return dataSource.getCurrentUserProfile()
    }
}