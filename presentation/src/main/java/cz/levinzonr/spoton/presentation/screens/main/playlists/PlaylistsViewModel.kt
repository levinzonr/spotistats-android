package cz.levinzonr.spoton.presentation.screens.main.playlists

import cz.levinzonr.spoton.domain.interactors.AddTracksToPlaylistInteractor
import cz.levinzonr.spoton.domain.interactors.GetPlaylistsInteractor
import cz.levinzonr.spoton.models.PlaylistResponse
import cz.levinzonr.spoton.presentation.base.BaseViewModel
import cz.levinzonr.spoton.presentation.extensions.flowOnIO
import cz.levinzonr.spoton.presentation.extensions.isError
import cz.levinzonr.spoton.presentation.extensions.isSuccess
import cz.levinzonr.spoton.presentation.extensions.toErrorEvent
import cz.levinzonr.spoton.presentation.util.SingleEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class PlaylistsViewModel(
        private val trackIds: Array<String>,
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
            is Action.PlaylistClicked -> bindPlaylistClicked(action.playlistResponse, action.skip)
        }
    }

    private fun bindInitAction(): Flow<Change> = flowOnIO {
        emit(Change.PlaylistsLoading)
        getPlaylistsInteractor.invoke()
                .isError { emit(Change.PlaylistsLoadingError(it)) }
                .isSuccess { emit(Change.PlaylistsLoaded(it)) }
    }

    private fun bindPlaylistClicked(playlistResponse: PlaylistResponse, skipRepeated: Boolean? = null): Flow<Change> = flowOnIO {
        addTracksToPlaylistInteractor.input = AddTracksToPlaylistInteractor.Input(playlistResponse, trackIds.toList(), skipRepeated)
        addTracksToPlaylistInteractor.invoke()
                .isError { emit(Change.TrackAdditionError(it)) }
                .isSuccess {
                    val change = if (it) Change.TrackAdded else Change.TrackAlreadyAdded(playlistResponse)
                    emit(change)
                }
    }
}