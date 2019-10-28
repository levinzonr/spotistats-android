package cz.levinzonr.spoton.domain.interactors

import cz.levinzonr.spoton.domain.models.Tracks
import cz.levinzonr.spoton.domain.extensions.safeInteractorCall
import cz.levinzonr.spoton.models.TimeRange
import cz.levinzonr.spoton.repositories.UserTopRepository

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