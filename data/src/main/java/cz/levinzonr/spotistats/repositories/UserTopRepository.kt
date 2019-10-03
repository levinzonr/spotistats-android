package cz.levinzonr.spotistats.repositories

import cz.levinzonr.spotistats.models.Item
import cz.levinzonr.spotistats.models.PaginatedResponse
import cz.levinzonr.spotistats.models.TimeRange
import cz.levinzonr.spotistats.models.TrackDataResponse
import cz.levinzonr.spotistats.models.TrackResponse

interface UserTopRepository {

    suspend fun getUserTopTracks(timeRange: TimeRange) : PaginatedResponse<Item>

}