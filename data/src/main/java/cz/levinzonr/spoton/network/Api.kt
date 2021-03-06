package cz.levinzonr.spoton.network

import cz.levinzonr.spoton.models.*
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("v1/me/top/tracks")
    suspend fun getTopUserTracksAsync(@Query("time_range") timeRange: String) : PaginatedResponse<TrackResponse>

    @GET("v1/me")
    suspend fun getCurrentUserProfile() : UserResponse

    @GET("v1/audio-features/{id}")
    suspend fun getTrackFeatures(@Path("id") trackId: String) : TrackFeaturesResponse

    @GET("v1/tracks/{id}")
    suspend fun getTrackDetails(@Path("id") trackId: String) : TrackResponse


    @GET("v1/me/playlists")
    suspend fun getUserPlaylists() : PaginatedResponse<PlaylistResponse>

    @POST("v1/users/{userId}/playlists")
    suspend fun createPlaylist(@Path("userId") userId: String, @Body playlistRequest: CreatePlaylistRequest) : PlaylistResponse

    @GET("v1/playlists/{id}")
    suspend fun getPlaylistById(@Path("id") id: String) : PlaylistResponse

    @POST("v1/playlists/{playlist_id}/tracks")
    suspend fun addTrackToPlaylist(
            @Path("playlist_id") playlistId: String,
            @Body addTracksToPlaylistRequest: AddTracksToPlaylistRequest
    ) : PlaylistUpdatedResponse

    @GET("v1/recommendations")
    suspend fun getRecommendedTracks(
            @Query("seed_artists") seedArtistsIds: List<String>,
            @Query("seed_tracks") seedTracks: List<String>,
            @Query("seed_genres") seedGenres: List<String>) : RecommendedTracks
}
