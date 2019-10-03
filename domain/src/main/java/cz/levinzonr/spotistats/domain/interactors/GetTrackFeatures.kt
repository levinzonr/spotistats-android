package cz.levinzonr.spotistats.domain.interactors

import cz.levinzonr.spotistats.domain.extensions.safeInteractorCall
import cz.levinzonr.spotistats.models.TrackFeaturesResponse
import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.network.Api

class GetTrackFeatures(private val api: Api) : BaseAsyncInteractor<CompleteResult<TrackFeaturesResponse>> {


    data class Input(val trackId: String)

    var input: Input? = null

    override suspend fun invoke(): CompleteResult<TrackFeaturesResponse> = safeInteractorCall {
        requireNotNull(input).let { input ->
            api.getTrackFeatures(input.trackId)
        }
    }
}