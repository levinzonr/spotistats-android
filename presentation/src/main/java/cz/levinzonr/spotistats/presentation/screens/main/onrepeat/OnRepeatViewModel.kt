package cz.levinzonr.spotistats.presentation.screens.main.onrepeat

import cz.levinzonr.spotistats.domain.interactors.GetUserTopTracksInteractor
import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.presentation.base.BaseViewModel
import cz.levinzonr.spotistats.presentation.extensions.flowOnIO
import cz.levinzonr.spotistats.presentation.extensions.isError
import cz.levinzonr.spotistats.presentation.extensions.isSuccess
import cz.levinzonr.spotistats.presentation.extensions.toErrorEvent
import cz.levinzonr.spotistats.presentation.navigation.Route
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OnRepeatViewModel(
        private val getUserTopTracksInteractor: GetUserTopTracksInteractor
) : BaseViewModel<Action, Change, State>() {


    override val initialState: State = State()

    override val reducer: suspend (state: State, change: Change) -> State = { state, change ->
        when (change) {
            is Change.LoadingStarted -> state.copy(isLoading = true)
            is Change.TracksLoaded -> state.copy(tracks = change.items)
            is Change.TracksLoadingError -> state.copy(error = change.throwable.toErrorEvent())
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