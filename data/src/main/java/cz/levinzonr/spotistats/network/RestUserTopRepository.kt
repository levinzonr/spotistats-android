package cz.levinzonr.spotistats.network

import cz.levinzonr.spotistats.models.PaginatedResponse
import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.repositories.UserTopRepository
import retrofit2.HttpException

class RestUserTopRepository(private val api: Api) : UserTopRepository {

    override suspend fun getUserTopTracks(): PaginatedResponse<TrackResponse> {
        return api.getTopUserTracksAsync().await()
    }
}