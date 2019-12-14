package cz.levinzonr.spoton.domain.interactors.player

import cz.levinzonr.spoton.domain.extensions.safeInteractorCall
import cz.levinzonr.spoton.domain.interactors.BaseAsyncInteractor
import cz.levinzonr.spoton.domain.interactors.CompleteResult
import cz.levinzonr.spoton.domain.managers.SpotifyRemoteManager

class PlayPreviousInteractor(
        private val spotify: SpotifyRemoteManager
) : BaseAsyncInteractor<CompleteResult<Unit>> {

    override suspend fun invoke() : CompleteResult<Unit> = safeInteractorCall {
        spotify.previous()
    }
}