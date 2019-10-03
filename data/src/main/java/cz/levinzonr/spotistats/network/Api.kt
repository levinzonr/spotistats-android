package cz.levinzonr.spotistats.network

import cz.levinzonr.spotistats.models.Item
import cz.levinzonr.spotistats.models.PaginatedResponse
import cz.levinzonr.spotistats.models.Photo
import cz.levinzonr.spotistats.models.Post
import cz.levinzonr.spotistats.models.TrackDataResponse
import cz.levinzonr.spotistats.models.TrackResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("photos")
    suspend fun getPhotos(): Response<List<Photo>>

    @GET("v1/me/top/tracks")
    suspend fun getTopUserTracksAsync(@Query("time_range") timeRange: String) : PaginatedResponse<Item>
}
