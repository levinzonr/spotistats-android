package cz.levinzonr.spoton.domain.interactors

import cz.levinzonr.spoton.domain.extensions.safeInteractorCall
import cz.levinzonr.spoton.domain.extensions.spotifyTrackUri
import cz.levinzonr.spoton.models.PlaylistResponse
import cz.levinzonr.spoton.network.SpotifyApi

class AddTracksToPlaylistInteractor(
        private val api: SpotifyApi
) : BaseAsyncInteractor<CompleteResult<Boolean>> {

    data class Input(val playlist: PlaylistResponse, val ids: List<String>, val forceAdd: Boolean = false)

    var input: Input? = null

    override suspend fun invoke(): CompleteResult<Boolean> = safeInteractorCall {
        requireNotNull(input).let { input ->
            val playlist = api.getPlaylistById(input.playlist.id)
            val uris = playlist.tracks.items.map { it.track.id }
            val noDuplicates = uris.none { input.ids.contains(it) }
            if (noDuplicates || input.forceAdd) {
                val r = api.addTrackToPlaylist(input.playlist.id, input.ids.map { it.spotifyTrackUri })
                return@safeInteractorCall true
            } else return@safeInteractorCall false
        }
    }
}