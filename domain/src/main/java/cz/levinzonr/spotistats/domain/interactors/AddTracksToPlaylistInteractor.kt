package cz.levinzonr.spotistats.domain.interactors

import cz.levinzonr.spotistats.domain.extensions.safeInteractorCall
import cz.levinzonr.spotistats.domain.extensions.spotifyTrackUri
import cz.levinzonr.spotistats.models.PlaylistResponse
import cz.levinzonr.spotistats.network.Api

class AddTracksToPlaylistInteractor(
        private val api: Api
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