package cz.levinzonr.spotistats.domain.interactors

import cz.levinzonr.spotistats.domain.extensions.safeInteractorCall
import cz.levinzonr.spotistats.models.Artist
import cz.levinzonr.spotistats.models.RecommendedTracks
import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.network.Api

class GetRecommendedTracks(private val api: Api) : BaseAsyncInteractor<CompleteResult<RecommendedTracks>> {

    data class Input(val tracks: List<TrackResponse>,
                     val artists: List<Artist>,
                     val genres: List<String>)

    var input: Input? = null

    override suspend fun invoke(): CompleteResult<RecommendedTracks> = safeInteractorCall {
        requireNotNull(input).let { input ->
            api.getRecommendedTracks(
                    seedArtistsIds = input.artists.map { it.id },
                    seedGenres = input.genres,
                    seedTracks = input.tracks.map { it.id }
            )
        }
    }
}