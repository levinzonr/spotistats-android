package cz.levinzonr.spotistats.network

import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.models.PaginatedResponse
import cz.levinzonr.spotistats.models.TimeRange
import cz.levinzonr.spotistats.repositories.UserTopRepository

class RestUserTopRepository(private val api: Api) : UserTopRepository {

    override suspend fun getUserTopTracks(timeRange: TimeRange): PaginatedResponse<TrackResponse> {
        val timeRange = when(timeRange) {
            TimeRange.Mid -> "medium_term"
            TimeRange.Long -> "long_term"
            TimeRange.Short -> "short_term"
        }
        return api.getTopUserTracksAsync(timeRange)
    }
}