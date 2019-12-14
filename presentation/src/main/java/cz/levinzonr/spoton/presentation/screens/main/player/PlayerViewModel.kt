package cz.levinzonr.spoton.presentation.screens.main.player

import cz.levinzonr.spoton.domain.interactors.GetTrackDetailsInteractor
import cz.levinzonr.spoton.domain.interactors.player.PlayNextInteractor
import cz.levinzonr.spoton.domain.interactors.player.PlayPreviousInteractor
import cz.levinzonr.spoton.domain.interactors.player.TogglePlayInteractor
import cz.levinzonr.spoton.domain.managers.SpotifyRemoteManager
import cz.levinzonr.spoton.domain.models.RemotePlayerState
import cz.levinzonr.spoton.presentation.base.BaseViewModel
import cz.levinzonr.spoton.presentation.extensions.flowOnIO
import cz.levinzonr.spoton.presentation.extensions.flowOnMain
import cz.levinzonr.spoton.presentation.extensions.isError
import cz.levinzonr.spoton.presentation.extensions.isSuccess
import cz.levinzonr.spoton.presentation.navigation.Route
import cz.levinzonr.spoton.presentation.screens.main.profile.ProfileFragmentDirections
import cz.levinzonr.spoton.presentation.util.SingleEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class PlayerViewModel(
        private val spotifyRemoteManager: SpotifyRemoteManager,
        private val getTrackDetailsInteractor: GetTrackDetailsInteractor,
        private val playNextInteractor: PlayNextInteractor,
        private val togglePlayInteractor: TogglePlayInteractor,
        private val playPreviousInteractor: PlayPreviousInteractor
) : BaseViewModel<Action, Change, State>() {

    override val initialState: State = State()
    override val reducer: suspend (state: State, change: Change) -> State = { state, change ->
        when (change) {
            is Change.RemotePlayerReading -> state.copy(playerState = null, isLoading = true)
            is Change.RemotePlayerError -> state.copy(error = change.throwable, isLoading = false)
            is Change.RemotePlayerReady -> state.copy(playerState = change.state, isLoading = false, error = null)
            is Change.TrackDetailsLoaded -> state.copy(currentTrack = change.trackResponse)
            is Change.PlayerActionSuccess -> state
            is Change.Navigation -> state.also { navigateTo(change.route) }
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
        return when (action) {
            is Action.PlayerTrackActionPressed -> bindPlayerTrackPressedAction(action.trackId)
            is Action.RemotePlayerStateUpdated -> bindRemoteStateUpdate(action.remotePlayerState)
            is Action.NextTrackPressed -> bindNextTrackPressed()
            is Action.PreviousTrackPressed ->  bindPreviousTrackPressed()
            is Action.PlayTrackPressed -> bindPlayTrackPressed()
            is Action.RetryConnectionPressed -> flowOnMain {
                spotifyRemoteManager.disconnect()
                spotifyRemoteManager.connect()
            }
        }
    }


    private fun bindNextTrackPressed() : Flow<Change> = flowOnIO {
        playNextInteractor()
                .isError { emit(Change.PlayerActionError(it)) }
    }

    private fun bindPreviousTrackPressed() : Flow<Change> = flowOnIO {
        playPreviousInteractor()
                .isError { emit(Change.PlayerActionError(it)) }
    }

    private fun bindPlayTrackPressed() : Flow<Change> = flowOnIO {
        togglePlayInteractor()
                .isError { emit(Change.PlayerActionError(it)) }
    }

    private fun bindRemoteStateUpdate(remotePlayerState: RemotePlayerState): Flow<Change> = flowOnIO {
        when (remotePlayerState) {
            is RemotePlayerState.Ready -> {
                emit(Change.RemotePlayerReady(remotePlayerState.state))
                val id = remotePlayerState.state.track.uri.split(":").last()
                if (currentState.currentTrack?.id != id) {
                    getTrackDetailsInteractor.input = GetTrackDetailsInteractor.Input(id)
                    getTrackDetailsInteractor()
                            .isSuccess { emit(Change.TrackDetailsLoaded(it)) }
                            .isError { Timber.e(it) }
                }
            }
            is RemotePlayerState.Error -> emit(Change.RemotePlayerError(remotePlayerState.throwable
                    ?: Exception()))
            is RemotePlayerState.Initilizing -> emit(Change.RemotePlayerReading)
        }
    }


    private fun bindPlayerTrackPressedAction(id: String) : Flow<Change> = flow {
        val route = Route.Destination(ProfileFragmentDirections.actionProfileFragmentToTrackDetailsFragment(id))
        emit(Change.Navigation(route))
    }

    override fun onCleared() {
        super.onCleared()
        spotifyRemoteManager.disconnect()
    }
}