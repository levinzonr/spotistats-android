package cz.levinzonr.spoton.domain.interactors

import cz.levinzonr.spoton.domain.extensions.safeInteractorCall
import cz.levinzonr.spoton.models.TrackResponse
import cz.levinzonr.spoton.network.Api

class GetTrackDetailsInteractor(private val api: Api) : BaseAsyncInteractor<CompleteResult<TrackResponse>> {

    data class Input(val trackId: String)

    var input: Input? = null

    override suspend fun invoke(): CompleteResult<TrackResponse> = safeInteractorCall {
        api.getTrackDetails(requireNotNull(input).trackId)
    }
}