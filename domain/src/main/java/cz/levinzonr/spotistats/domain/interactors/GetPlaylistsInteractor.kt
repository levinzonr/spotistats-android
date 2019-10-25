package cz.levinzonr.spotistats.domain.interactors

import cz.levinzonr.spotistats.domain.extensions.safeInteractorCall
import cz.levinzonr.spotistats.models.PlaylistResponse
import cz.levinzonr.spotistats.network.Api

class GetPlaylistsInteractor(private val api: Api) : BaseAsyncInteractor<CompleteResult<List<PlaylistResponse>>> {

    override suspend fun invoke(): CompleteResult<List<PlaylistResponse>> = safeInteractorCall {
        api.getUserPlaylists().items
    }
}