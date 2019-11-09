package cz.levinzonr.spoton.domain.interactors

import cz.levinzonr.spoton.domain.extensions.safeInteractorCall
import cz.levinzonr.spoton.domain.extensions.spotifyTrackUri
import cz.levinzonr.spoton.models.PlaylistResponse
import cz.levinzonr.spoton.network.Api

class AddTracksToPlaylistInteractor(
        private val api: Api
) : BaseAsyncInteractor<CompleteResult<Boolean>> {

    data class Input(val playlist: PlaylistResponse, val ids: List<String>, val skipRepeated: Boolean? = null)

    var input: Input? = null

    override suspend fun invoke(): CompleteResult<Boolean> = safeInteractorCall {
        requireNotNull(input).let { input ->
            val playlist = api.getPlaylistById(input.playlist.id)
            val uris = playlist.tracks.items.map { it.track.id }
            val noDuplicates = uris.none { input.ids.contains(it) }
            if (input.skipRepeated == null) {

                // just add
                if (noDuplicates) {
                    api.addTrackToPlaylist(input.playlist.id, input.ids.map { it.spotifyTrackUri })
                    return@safeInteractorCall true
                } else {
                    return@safeInteractorCall false
                }

            } else {
                val toAdd = if (input.skipRepeated) input.ids.filterNot { uris.contains(it) } else input.ids
                if (toAdd.isEmpty()) return@safeInteractorCall true
                api.addTrackToPlaylist(input.playlist.id, toAdd.map { it.spotifyTrackUri })
                return@safeInteractorCall true

            }
        }
    }
}