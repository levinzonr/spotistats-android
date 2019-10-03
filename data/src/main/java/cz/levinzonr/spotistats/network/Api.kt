package cz.levinzonr.spotistats.network

import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.models.PaginatedResponse
import cz.levinzonr.spotistats.models.Photo
import cz.levinzonr.spotistats.models.Post
import cz.levinzonr.spotistats.models.RecommendedTracks
import cz.levinzonr.spotistats.models.TrackFeaturesResponse
import cz.levinzonr.spotistats.models.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("photos")
    suspend fun getPhotos(): Response<List<Photo>>

    @GET("v1/me/top/tracks")
    suspend fun getTopUserTracksAsync(@Query("time_range") timeRange: String) : PaginatedResponse<TrackResponse>

    @GET("v1/me")
    suspend fun getCurrentUserProfile() : UserResponse

    @GET("v1/audio-features/{id}")
    suspend fun getTrackFeatures(@Path("id") trackId: String) : TrackFeaturesResponse

    @GET("v1/tracks/{id}")
    suspend fun getTrackDetails(@Path("id") trackId: String) : TrackResponse

    @GET("v1/recommendations")
    suspend fun getRecommendedTracks(
            @Query("seed_artists") seedArtistsIds: List<String>,
            @Query("seed_tracks") seedTracks: List<String>,
            @Query("seed_genres") seedGenres: List<String>) : RecommendedTracks
}
