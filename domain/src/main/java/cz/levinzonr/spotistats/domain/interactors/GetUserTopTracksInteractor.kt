package cz.levinzonr.spotistats.domain.interactors

import cz.levinzonr.spotistats.domain.extensions.safeInteractorCall
import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.repositories.UserTopRepository

class GetUserTopTracksInteractor constructor(
        private val repository: UserTopRepository
) : BaseAsyncInteractor<CompleteResult<List<TrackResponse>>> {

    override suspend fun invoke(): CompleteResult<List<TrackResponse>> = safeInteractorCall {
        repository.getUserTopTracks().items
    }
}