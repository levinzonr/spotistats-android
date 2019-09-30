package cz.levinzonr.spotistats.network

import cz.levinzonr.spotistats.models.PaginatedResponse
import cz.levinzonr.spotistats.models.Photo
import cz.levinzonr.spotistats.models.Post
import cz.levinzonr.spotistats.models.TrackResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("photos")
    suspend fun getPhotos(): Response<List<Photo>>

    @GET("v1/me/top/track")
    suspend fun getTopUserTracks() : Deferred<PaginatedResponse<TrackResponse>>
}
