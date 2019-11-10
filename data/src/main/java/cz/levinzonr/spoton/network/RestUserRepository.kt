package cz.levinzonr.spoton.network

import cz.levinzonr.spoton.models.UserResponse
import cz.levinzonr.spoton.repositories.PrefManager
import cz.levinzonr.spoton.repositories.UserRepository

class RestUserRepository(
        private val dataSource: Api) : UserRepository {

    override suspend fun getCurrentUserProfile(): UserResponse {
        return dataSource.getCurrentUserProfile()
    }
}