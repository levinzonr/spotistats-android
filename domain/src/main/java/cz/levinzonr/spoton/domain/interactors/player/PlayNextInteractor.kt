package cz.levinzonr.spoton.domain.interactors.player

import cz.levinzonr.spoton.domain.extensions.safeInteractorCall
import cz.levinzonr.spoton.domain.interactors.BaseAsyncInteractor
import cz.levinzonr.spoton.domain.interactors.CompleteResult
import cz.levinzonr.spoton.domain.managers.SpotifyRemoteManager

class PlayNextInteractor(
        private val spotify: SpotifyRemoteManager
) : BaseAsyncInteractor<CompleteResult<Unit>> {

    override suspend fun invoke(): CompleteResult<Unit> = safeInteractorCall {
        spotify.next()
    }
}