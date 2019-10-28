package cz.levinzonr.spoton.presentation.screens.main.profile

import cz.levinzonr.spoton.domain.interactors.GetPlaylistsInteractor
import cz.levinzonr.spoton.domain.interactors.GetUserProfileInteractor
import cz.levinzonr.spoton.domain.managers.SpotifyRemoteManager
import cz.levinzonr.spoton.models.PlaylistResponse
import cz.levinzonr.spoton.presentation.base.BaseViewModel
import cz.levinzonr.spoton.presentation.extensions.first
import cz.levinzonr.spoton.presentation.extensions.flowOnIO
import cz.levinzonr.spoton.presentation.extensions.isError
import cz.levinzonr.spoton.presentation.extensions.isSuccess
import cz.levinzonr.spoton.presentation.navigation.Route
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileViewModel(
        private val spotifyRemoteManager: SpotifyRemoteManager,
        private val getPlaylistsInteractor: GetPlaylistsInteractor,
        private val getUserProfileInteractor: GetUserProfileInteractor)
    : BaseViewModel<Action, Change, State>() {

    override val initialState: State = State()

    override val reducer: suspend (state: State, change: Change) -> State = { state, change ->
        when (change) {
            is Change.ProfileLoading -> state.copy(isLoading = true)
            is Change.ProfileLoaded -> state.copy(isLoading = false, user = change.user)
            is Change.ProfileLoadingError -> state.copy(isLoading = false)

            is Change.Navigation -> state.also { navigateTo(change.route) }


            is Change.RecentPlaylistsError -> state.copy()
            is Change.RecentPlaylistsLoded -> state.copy(recentPlaylists = change.playlists)
        }
    }

    init {
        startActionsObserver()
        dispatch(Action.Init)
    }

    override fun emitAction(action: Action): Flow<Change> {
        return when (action) {
            is Action.Init -> bindInitAction()
            is Action.SettingsPressed -> bindSettingsClickActions()
            is Action.PlaylistPlayButtonPressed -> bindPlayPlaylistAction(action.playlist, action.shuffled)
        }
    }


    private fun bindPlayPlaylistAction(playlist: PlaylistResponse, shuffled: Boolean): Flow<Change> = flow {
        spotifyRemoteManager.play("spotify:playlist:${playlist.id}")
        spotifyRemoteManager.shuffle(shuffled)

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


}