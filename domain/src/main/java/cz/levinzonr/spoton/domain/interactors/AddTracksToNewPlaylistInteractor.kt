package cz.levinzonr.spoton.domain.interactors

import cz.levinzonr.spoton.domain.extensions.safeInteractorCall
import cz.levinzonr.spoton.domain.extensions.spotifyTrackUri
import cz.levinzonr.spoton.models.AddTracksToPlaylistRequest
import cz.levinzonr.spoton.models.CreatePlaylistRequest
import cz.levinzonr.spoton.models.PlaylistUpdatedResponse
import cz.levinzonr.spoton.network.Api
import cz.levinzonr.spoton.repositories.UserRepository

class AddTracksToNewPlaylistInteractor(
        private val userRepository: UserRepository,
        private val api: Api) : BaseAsyncInteractor<CompleteResult<PlaylistUpdatedResponse>> {

    data class Input(val name: String, val tracks: List<String>)

    var input: Input? = null

    override suspend fun invoke(): CompleteResult<PlaylistUpdatedResponse> = safeInteractorCall {
        requireNotNull(input).let { input ->
            val request = CreatePlaylistRequest(input.name, "Playlist created by SpotOn Application")
            val userId = userRepository.getCurrentUserProfile().id
            val playlist = api.createPlaylist(userId, request)
            api.addTrackToPlaylist(playlist.id, AddTracksToPlaylistRequest(input.tracks.map { it.spotifyTrackUri }))
        }
    }
}