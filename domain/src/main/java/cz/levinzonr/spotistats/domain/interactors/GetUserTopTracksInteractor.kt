package cz.levinzonr.spotistats.domain.interactors

import cz.levinzonr.spotistats.domain.Tracks
import cz.levinzonr.spotistats.domain.extensions.safeInteractorCall
import cz.levinzonr.spotistats.models.TimeRange
import cz.levinzonr.spotistats.repositories.UserTopRepository

class GetUserTopTracksInteractor constructor(
        private val repository: UserTopRepository
) : BaseAsyncInteractor<CompleteResult<Tracks>> {


    override suspend fun invoke(): CompleteResult<Tracks> = safeInteractorCall {
        val short = repository.getUserTopTracks(TimeRange.Short).items
        val long = repository.getUserTopTracks(TimeRange.Long).items
        val mid = repository.getUserTopTracks(TimeRange.Mid).items
        return@safeInteractorCall Tracks(short, long, mid)
    }
}