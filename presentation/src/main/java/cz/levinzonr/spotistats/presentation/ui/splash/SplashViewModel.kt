package cz.levinzonr.spotistats.presentation.ui.splash

import cz.levinzonr.spotistats.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SplashViewModel@Inject constructor() : BaseViewModel<Action, Change, State>() {

    override val initialState: State = State(false)

    override val reducer: suspend (state: State, change: Change) -> State = {state, change ->
        when(change) {
            is Change.One -> state.copy(doneLoading = true)
        }
    }

    override fun emitAction(action: Action): Flow<Change> {
        return flowOf()
    }
}