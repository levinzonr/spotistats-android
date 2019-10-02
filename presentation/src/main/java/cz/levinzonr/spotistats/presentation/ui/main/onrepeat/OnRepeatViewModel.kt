package cz.levinzonr.spotistats.presentation.ui.main.onrepeat

import cz.levinzonr.spotistats.domain.interactors.GetUserTopTracksInteractor
import cz.levinzonr.spotistats.presentation.base.BaseViewModel
import cz.levinzonr.spotistats.presentation.extensions.flowOnIO
import cz.levinzonr.spotistats.presentation.extensions.isError
import cz.levinzonr.spotistats.presentation.extensions.isSuccess
import cz.levinzonr.spotistats.presentation.extensions.toErrorEvent
import kotlinx.coroutines.flow.Flow

class OnRepeatViewModel(
        private val getUserTopTracksInteractor: GetUserTopTracksInteractor
) : BaseViewModel<Action, Change, State>() {


    override val initialState: State = State()

    override val reducer: suspend (state: State, change: Change) -> State = { state, change ->
        when (change) {
            is Change.LoadingStarted -> state.copy(isLoading = true)
            is Change.TracksLoaded -> state.copy(items = change.items)
            is Change.TracksLoadingError -> state.copy(error = change.throwable.toErrorEvent())
        }
    }


    init {
        startActionsObserver()
        dispatch(Action.Init)
    }

    override fun emitAction(action: Action): Flow<Change> {
        return when (action) {
            Action.Init -> bindLoadTracksAction()
        }
    }


    private fun bindLoadTracksAction(): Flow<Change> = flowOnIO {
        emit(Change.LoadingStarted)
        getUserTopTracksInteractor()
                .isError { emit(Change.TracksLoadingError(it)) }
                .isSuccess { emit(Change.TracksLoaded(it)) }
    }
}