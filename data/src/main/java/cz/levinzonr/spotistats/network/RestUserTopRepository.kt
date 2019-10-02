package cz.levinzonr.spotistats.network

import cz.levinzonr.spotistats.models.Item
import cz.levinzonr.spotistats.models.PaginatedResponse
import cz.levinzonr.spotistats.repositories.UserTopRepository

class RestUserTopRepository(private val api: Api) : UserTopRepository {

    override suspend fun getUserTopTracks(): PaginatedResponse<Item> {
        return api.getTopUserTracksAsync()
    }
}