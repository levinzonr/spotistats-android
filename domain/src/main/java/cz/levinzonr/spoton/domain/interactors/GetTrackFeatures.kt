package cz.levinzonr.spoton.domain.interactors

import cz.levinzonr.spoton.domain.extensions.safeInteractorCall
import cz.levinzonr.spoton.models.TrackFeaturesResponse
import cz.levinzonr.spoton.network.SpotifyApi

class GetTrackFeatures(private val api: SpotifyApi) : BaseAsyncInteractor<CompleteResult<TrackFeaturesResponse>> {


    data class Input(val trackId: String)

    var input: Input? = null

    override suspend fun invoke(): CompleteResult<TrackFeaturesResponse> = safeInteractorCall {
        requireNotNull(input).let { input ->
            api.getTrackFeatures(input.trackId)
        }
    }
}