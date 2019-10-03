package cz.levinzonr.spotistats.repositories

import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.models.PaginatedResponse
import cz.levinzonr.spotistats.models.TimeRange

interface UserTopRepository {

    suspend fun getUserTopTracks(timeRange: TimeRange) : PaginatedResponse<TrackResponse>

}