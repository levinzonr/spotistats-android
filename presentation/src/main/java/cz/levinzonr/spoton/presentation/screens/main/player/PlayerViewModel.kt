package cz.levinzonr.spoton.presentation.screens.main.player

import cz.levinzonr.spoton.domain.interactors.GetTrackDetailsInteractor
import cz.levinzonr.spoton.domain.managers.SpotifyRemoteManager
import cz.levinzonr.spoton.domain.models.RemotePlayerState
import cz.levinzonr.spoton.presentation.base.BaseViewModel
import cz.levinzonr.spoton.presentation.extensions.flowOnIO
import cz.levinzonr.spoton.presentation.extensions.isError
import cz.levinzonr.spoton.presentation.extensions.isSuccess
import cz.levinzonr.spoton.presentation.extensions.toErrorEvent
import cz.levinzonr.spoton.presentation.util.SingleEvent
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
            is Change.RemotePlayerReady -> state.copy(playerState = change.state, currentTrack = null)
            is Change.TrackDetailsLoaded -> state.copy(currentTrack = change.trackResponse)
            is Change.PlayerActionSuccess -> state
            is Change.PlayerActionError -> state.copy(toast = SingleEvent(change.throwable.localizedMessage))
        }
    }

    init {
        startActionsObserver()
        spotifyRemoteManager.connect()
        addStateSource(spotifyRemoteManager.stateLiveData) {
            dispatch(Action.RemotePlayerStateUpdated(it))
        }
    }

    override fun emitAction(action: Action): Flow<Change> {
       return when(action) {
            is Action.RemotePlayerStateUpdated -> bindRemoteStateUpdate(action.remotePlayerState)
            is Action.NextTrackPressed -> flowOnIO { spotifyRemoteManager.next() }
            is Action.PreviousTrackPressed -> flowOnIO { spotifyRemoteManager.previous() }
            is Action.PlayTrackPressed -> flowOnIO { spotifyRemoteManager.toggle() }
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

    override fun onCleared() {
        super.onCleared()
        spotifyRemoteManager.disconnect()
    }
}