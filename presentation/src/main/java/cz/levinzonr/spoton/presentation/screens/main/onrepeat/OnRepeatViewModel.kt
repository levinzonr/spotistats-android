package cz.levinzonr.spoton.presentation.screens.main.onrepeat

import cz.levinzonr.spoton.domain.interactors.AddTracksToNewPlaylistInteractor
import cz.levinzonr.spoton.domain.interactors.GetUserTopTracksInteractor
import cz.levinzonr.spoton.models.TrackResponse
import cz.levinzonr.spoton.presentation.base.BaseViewModel
import cz.levinzonr.spoton.presentation.extensions.flowOnIO
import cz.levinzonr.spoton.presentation.extensions.isError
import cz.levinzonr.spoton.presentation.extensions.isSuccess
import cz.levinzonr.spoton.presentation.extensions.toErrorEvent
import cz.levinzonr.spoton.presentation.navigation.Route
import cz.levinzonr.spoton.presentation.util.SingleEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class OnRepeatViewModel(
        private val getUserTopTracksInteractor: GetUserTopTracksInteractor,
        private val addTracksToNewPlaylistInteractor: AddTracksToNewPlaylistInteractor
) : BaseViewModel<Action, Change, State>() {


    override val initialState: State = State()

    override val reducer: suspend (state: State, change: Change) -> State = { state, change ->
        when (change) {
            is Change.LoadingStarted -> state.copy(isLoading = true)
            is Change.TracksLoaded -> state.copy(tracks = change.items, isLoading = false)
            is Change.TracksLoadingError -> state.copy(error = change.throwable.toErrorEvent(), isLoading = false)
            is Change.Navigation -> state.also { navigateTo(change.route) }
            is Change.PlaylistCreated -> state.copy(playlistCreated = SingleEvent(Unit))
        }
    }


    init {
        startActionsObserver()
        dispatch(Action.Init)
    }

    override fun emitAction(action: Action): Flow<Change> {
        return when (action) {
            is Action.Init -> bindLoadTracksAction()
            is Action.TrackClicked -> bindTrackClickAction(action.track)
            is Action.AddToPlaylistAction -> bindAddToPlaylistActions(action.tracks)
            is Action.CreatePlaylistAction -> bindCreateNewPlaylistAction(action.tracks, action.playlistName)
        }
    }


    private fun bindLoadTracksAction(): Flow<Change> = flowOnIO {
        emit(Change.LoadingStarted)
        getUserTopTracksInteractor()
                .isError { emit(Change.TracksLoadingError(it)) }
                .isSuccess { emit(Change.TracksLoaded(it)) }
    }

    private fun bindTrackClickAction(track: TrackResponse) : Flow<Change> = flow {
        val route = Route.Destination(OnRepeatFragmentDirections.actionOnRepeatFragmentToTrackDetailsFragment(track.id))
        emit(Change.Navigation(route))
    }

    private fun bindAddToPlaylistActions(tracks: List<TrackResponse>) : Flow<Change> = flow {
        val ids = tracks.map { it.id }.toTypedArray()
        val route = Route.Destination(OnRepeatFragmentDirections.actionOnRepeatFragmentToPlaylistsDialogFragment(ids))
        emit(Change.Navigation(route))
    }

    private fun bindCreateNewPlaylistAction(tracks: List<TrackResponse>, name: String) : Flow<Change> = flowOnIO {
        addTracksToNewPlaylistInteractor.input = AddTracksToNewPlaylistInteractor.Input(name, tracks.map { it.id })
        addTracksToNewPlaylistInteractor()
                .isError { Timber.e(it) }
                .isSuccess { emit(Change.PlaylistCreated) }
    }
}