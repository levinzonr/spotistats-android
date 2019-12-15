package cz.levinzonr.spoton.domain.interactors

import cz.levinzonr.spoton.domain.extensions.safeInteractorCall
import cz.levinzonr.spoton.domain.managers.SpotifyRemoteManager

class PlayPlaylistInteractor(
        private val spotifyRemoteManager: SpotifyRemoteManager
): BaseAsyncInteractor<CompleteResult<Unit>> {

    data class Input(val shuffle: Boolean, val id: String)

    var input: Input? = null

    override suspend fun invoke(): CompleteResult<Unit> = safeInteractorCall {
        spotifyRemoteManager.shuffle(requireNotNull(input).shuffle)
        spotifyRemoteManager.play(requireNotNull(input).id)
    }
}