package cz.levinzonr.spoton.presentation.screens.main.onrepeat

import cz.levinzonr.spoton.domain.interactors.GetUserTopTracksInteractor
import cz.levinzonr.spoton.models.TrackResponse
import cz.levinzonr.spoton.presentation.base.BaseViewModel
import cz.levinzonr.spoton.presentation.extensions.flowOnIO
import cz.levinzonr.spoton.presentation.extensions.isError
import cz.levinzonr.spoton.presentation.extensions.isSuccess
import cz.levinzonr.spoton.presentation.extensions.toErrorEvent
import cz.levinzonr.spoton.presentation.navigation.Route
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OnRepeatViewModel(
        private val getUserTopTracksInteractor: GetUserTopTracksInteractor
) : BaseViewModel<Action, Change, State>() {


    override val initialState: State = State()

    override val reducer: suspend (state: State, change: Change) -> State = { state, change ->
        when (change) {
            is Change.LoadingStarted -> state.copy(isLoading = true)
            is Change.TracksLoaded -> state.copy(tracks = change.items, isLoading = false)
            is Change.TracksLoadingError -> state.copy(error = change.throwable.toErrorEvent(), isLoading = false)
            is Change.Navigation -> state.also { navigateTo(change.route) }
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
}