package cz.levinzonr.spotistats.domain.interactors

import cz.levinzonr.spotistats.domain.extensions.safeInteractorCall
import cz.levinzonr.spotistats.models.Item
import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.repositories.UserTopRepository

class GetUserTopTracksInteractor constructor(
        private val repository: UserTopRepository
) : BaseAsyncInteractor<CompleteResult<List<Item>>> {

    override suspend fun invoke(): CompleteResult<List<Item>> = safeInteractorCall {
        repository.getUserTopTracks().items
    }
}