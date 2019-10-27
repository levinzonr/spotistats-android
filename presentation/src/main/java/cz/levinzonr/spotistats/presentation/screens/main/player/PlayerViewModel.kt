package cz.levinzonr.spotistats.presentation.screens.main.player

import cz.levinzonr.spotistats.domain.interactors.GetTrackDetailsInteractor
import cz.levinzonr.spotistats.domain.managers.SpotifyRemoteManager
import cz.levinzonr.spotistats.domain.models.RemotePlayerState
import cz.levinzonr.spotistats.presentation.base.BaseViewModel
import cz.levinzonr.spotistats.presentation.extensions.flowOnIO
import cz.levinzonr.spotistats.presentation.extensions.isError
import cz.levinzonr.spotistats.presentation.extensions.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class PlayerViewModel(
        private val spotifyRemoteManager: SpotifyRemoteManager,
        private val getTrackDetailsInteractor: GetTrackDetailsInteractor
) : BaseViewModel<Action, Change, State>() {

    override val initialState: State = State()
    override val reducer: suspend (state: State, change: Change) -> State = { state, change ->
        when (change) {
            is Change.RemotePlayerReading -> state.copy(playerState = null)
            is Change.RemotePlayerError -> state.copy(error = change.throwable)
            is Change.RemotePlayerReady -> state.copy(playerState = change.state)
            is Change.TrackDetailsLoaded -> state.copy(currentTrack = change.trackResponse)
        }
    }

    init {
        startActionsObserver()
        addStateSource(spotifyRemoteManager.stateLiveData) {
            dispatch(Action.RemotePlayerStateUpdated(it))
        }
    }

    override fun emitAction(action: Action): Flow<Change> {
       return when(action) {
            is Action.RemotePlayerStateUpdated -> bindRemoteStateUpdate(action.remotePlayerState)
            is Action.NextTrackPressed -> flow { spotifyRemoteManager.next() }
            is Action.PreviousTrackPressed -> flow { spotifyRemoteManager.previous() }
            is Action.PlayTrackPressed -> flow { spotifyRemoteManager.toggle() }
        }
    }


    private fun bindRemoteStateUpdate(remotePlayerState: RemotePlayerState): Flow<Change> = flowOnIO {
        when (remotePlayerState) {
            is RemotePlayerState.Ready -> {
                emit(Change.RemotePlayerReady(remotePlayerState.state))
                val id = remotePlayerState.state.track.uri.split(":").last()
                if (currentState.currentTrack?.id !=  id) {
                    getTrackDetailsInteractor.input = GetTrackDetailsInteractor.Input(id)
                    getTrackDetailsInteractor()
                            .isSuccess { emit(Change.TrackDetailsLoaded(it)) }
                            .isError { Timber.e(it) }
                }
            }
            is RemotePlayerState.Error -> emit(Change.RemotePlayerError(remotePlayerState.throwable ?: Exception()))
            is RemotePlayerState.Initilizing -> emit(Change.RemotePlayerReading)
        }
    }
}