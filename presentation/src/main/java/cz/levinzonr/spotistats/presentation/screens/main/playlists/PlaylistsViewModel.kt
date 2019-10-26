package cz.levinzonr.spotistats.presentation.screens.main.playlists

import cz.levinzonr.spotistats.domain.interactors.AddTracksToPlaylistInteractor
import cz.levinzonr.spotistats.domain.interactors.GetPlaylistsInteractor
import cz.levinzonr.spotistats.models.PlaylistResponse
import cz.levinzonr.spotistats.presentation.base.BaseViewModel
import cz.levinzonr.spotistats.presentation.extensions.flowOnIO
import cz.levinzonr.spotistats.presentation.extensions.isError
import cz.levinzonr.spotistats.presentation.extensions.isSuccess
import cz.levinzonr.spotistats.presentation.extensions.toErrorEvent
import cz.levinzonr.spotistats.presentation.util.SingleEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class PlaylistsViewModel(
        private val trackId: String,
        private val getPlaylistsInteractor: GetPlaylistsInteractor,
        private val addTracksToPlaylistInteractor: AddTracksToPlaylistInteractor
) : BaseViewModel<Action, Change, State>() {

    override val initialState: State = State()

    override val reducer: suspend (state: State, change: Change) -> State = { state, change ->
        when (change) {
            is Change.PlaylistsLoading -> state.copy(isLoading = true)
            is Change.PlaylistsLoadingError -> state.copy(error = change.throwable.toErrorEvent(), isLoading = false)
            is Change.PlaylistsLoaded -> state.copy(isLoading = false, playlists = change.playlists)
            is Change.TrackAlreadyAdded -> state.copy(duplicateDialog = SingleEvent(change.playlistResponse))
            is Change.TrackAdded -> state.copy(successEvent = SingleEvent(Unit))
            is Change.TrackAdditionError -> state.copy(error = change.throws.toErrorEvent())
        }

    }

    init {
        startActionsObserver()
        dispatch(Action.Init)
    }

    override fun emitAction(action: Action): Flow<Change> {
        return when (action) {
            is Action.Init -> bindInitAction()
            is Action.PlaylistClicked -> bindPlaylistClicked(action.playlistResponse, action.force)
        }
    }

    private fun bindInitAction(): Flow<Change> = flowOnIO {
        emit(Change.PlaylistsLoading)
        getPlaylistsInteractor.invoke()
                .isError { emit(Change.PlaylistsLoadingError(it)) }
                .isSuccess { emit(Change.PlaylistsLoaded(it)) }
    }

    private fun bindPlaylistClicked(playlistResponse: PlaylistResponse, force: Boolean): Flow<Change> = flowOnIO {
        addTracksToPlaylistInteractor.input = AddTracksToPlaylistInteractor.Input(playlistResponse, listOf(trackId), force)
        addTracksToPlaylistInteractor.invoke()
                .isError { emit(Change.TrackAdditionError(it)) }
                .isSuccess {
                    val change = if (it) Change.TrackAdded else Change.TrackAlreadyAdded(playlistResponse)
                    emit(change)
                }
    }
}