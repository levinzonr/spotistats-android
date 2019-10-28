package cz.levinzonr.spoton.repositories

import cz.levinzonr.spoton.models.TrackResponse
import cz.levinzonr.spoton.models.PaginatedResponse
import cz.levinzonr.spoton.models.TimeRange

interface UserTopRepository {

    suspend fun getUserTopTracks(timeRange: TimeRange) : PaginatedResponse<TrackResponse>

}