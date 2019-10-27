package cz.levinzonr.spotistats.presentation.screens.main.profile

import cz.levinzonr.spotistats.domain.interactors.GetPlaylistsInteractor
import cz.levinzonr.spotistats.domain.interactors.GetTrackDetailsInteractor
import cz.levinzonr.spotistats.domain.interactors.GetUserProfileInteractor
import cz.levinzonr.spotistats.domain.managers.SpotifyRemoteManager
import cz.levinzonr.spotistats.domain.managers.UserManager
import cz.levinzonr.spotistats.domain.models.RemotePlayerState
import cz.levinzonr.spotistats.presentation.base.BaseViewModel
import cz.levinzonr.spotistats.presentation.extensions.first
import cz.levinzonr.spotistats.presentation.extensions.flowOnIO
import cz.levinzonr.spotistats.presentation.extensions.isError
import cz.levinzonr.spotistats.presentation.extensions.isSuccess
import cz.levinzonr.spotistats.presentation.navigation.Route
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class ProfileViewModel(
        private val spotifyRemoteManager: SpotifyRemoteManager,
        private val getPlaylistsInteractor: GetPlaylistsInteractor,
        private val getUserProfileInteractor: GetUserProfileInteractor,
        private val getTrackDetailsInteractor: GetTrackDetailsInteractor)
    : BaseViewModel<Action, Change, State>() {

    override val initialState: State = State()

    override val reducer: suspend (state: State, change: Change) -> State = { state, change ->
        when (change) {
            is Change.ProfileLoading -> state.copy(isLoading = true)
            is Change.ProfileLoaded -> state.copy(isLoading = false, user = change.user)
            is Change.ProfileLoadingError -> state.copy(isLoading = false)

            is Change.Navigation -> state.also { navigateTo(change.route) }

            is Change.RemotePlayerReading -> state.copy(playerState = null)
            is Change.RemotePlayerError -> state.copy(playerState = null)
            is Change.RemotePlayerReady -> state.copy(playerState = change.state)
            is Change.TrackDetailsLoaded -> state.copy(currentTrack = change.trackResponse)

            is Change.RecentPlaylistsError -> state.copy()
            is Change.RecentPlaylistsLoded -> state.copy(recentPlaylists = change.playlists)
        }
    }

    init {
        startActionsObserver()
        addStateSource(spotifyRemoteManager.stateLiveData) {
            dispatch(Action.RemotePlayerStateUpdated(it))
        }
        dispatch(Action.Init)
    }

    override fun emitAction(action: Action): Flow<Change> {
        return when (action) {
            is Action.Init -> bindInitAction()
            is Action.SettingsPressed -> bindSettingsClickActions()
            is Action.RemotePlayerStateUpdated -> bindRemoteStateUpdate(action.remotePlayerState)
            is Action.NextTrackPressed -> flow { spotifyRemoteManager.next() }
            is Action.PreviousTrackPressed -> flow { spotifyRemoteManager.previous() }
            is Action.PlayTrackPressed -> flow { spotifyRemoteManager.toggle() }
        }
    }



    private fun bindInitAction(): Flow<Change> = flowOnIO {
        emit(Change.ProfileLoading)

        getPlaylistsInteractor()
                .isSuccess { emit(Change.RecentPlaylistsLoded(it.first(3))) }
                .isError { emit(Change.RecentPlaylistsError(it)) }

        getUserProfileInteractor()
                .isError { emit(Change.ProfileLoadingError(it)) }
                .isSuccess { emit(Change.ProfileLoaded(it)) }

    }

    private fun bindSettingsClickActions(): Flow<Change> = flow {
        val route = Route.Destination(ProfileFragmentDirections.actionProfileFragmentToSettingsFragment())
        emit(Change.Navigation(route))
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
            is RemotePlayerState.Error -> emit(Change.RemotePlayerError)
            is RemotePlayerState.Initilizing -> emit(Change.RemotePlayerReading)
        }
    }
}