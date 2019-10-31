package cz.levinzonr.spoton.domain.interactors

import cz.levinzonr.spoton.domain.extensions.safeInteractorCall
import cz.levinzonr.spoton.models.PlaylistResponse
import cz.levinzonr.spoton.network.SpotifyApi

class GetPlaylistsInteractor(private val api: SpotifyApi) : BaseAsyncInteractor<CompleteResult<List<PlaylistResponse>>> {

    override suspend fun invoke(): CompleteResult<List<PlaylistResponse>> = safeInteractorCall {
        api.getUserPlaylists().items
    }
}