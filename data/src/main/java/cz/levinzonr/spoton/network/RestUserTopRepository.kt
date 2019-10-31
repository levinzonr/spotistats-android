package cz.levinzonr.spoton.network

import cz.levinzonr.spoton.models.TrackResponse
import cz.levinzonr.spoton.models.PaginatedResponse
import cz.levinzonr.spoton.models.TimeRange
import cz.levinzonr.spoton.repositories.UserTopRepository

class RestUserTopRepository(private val api: SpotifyApi) : UserTopRepository {

    override suspend fun getUserTopTracks(timeRange: TimeRange): PaginatedResponse<TrackResponse> {
        val timeRange = when(timeRange) {
            TimeRange.Mid -> "medium_term"
            TimeRange.Long -> "long_term"
            TimeRange.Short -> "short_term"
        }
        return api.getTopUserTracksAsync(timeRange)
    }
}