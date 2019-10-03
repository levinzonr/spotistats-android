package cz.levinzonr.spotistats.domain.interactors

import cz.levinzonr.spotistats.domain.extensions.safeInteractorCall
import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.network.Api

class GetTrackDetailsInteractor(private val api: Api) : BaseAsyncInteractor<CompleteResult<TrackResponse>> {

    data class Input(val trackId: String)

    var input: Input? = null

    override suspend fun invoke(): CompleteResult<TrackResponse> = safeInteractorCall {
        api.getTrackDetails(requireNotNull(input).trackId)
    }
}