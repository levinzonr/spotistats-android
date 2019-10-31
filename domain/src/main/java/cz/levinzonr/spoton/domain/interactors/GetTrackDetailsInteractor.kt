package cz.levinzonr.spoton.domain.interactors

import cz.levinzonr.spoton.domain.extensions.safeInteractorCall
import cz.levinzonr.spoton.models.TrackResponse
import cz.levinzonr.spoton.network.SpotifyApi

class GetTrackDetailsInteractor(private val api: SpotifyApi) : BaseAsyncInteractor<CompleteResult<TrackResponse>> {

    data class Input(val trackId: String)

    var input: Input? = null

    override suspend fun invoke(): CompleteResult<TrackResponse> = safeInteractorCall {
        api.getTrackDetails(requireNotNull(input).trackId)
    }
}