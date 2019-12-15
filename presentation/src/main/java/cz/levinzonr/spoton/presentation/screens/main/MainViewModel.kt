package cz.levinzonr.spoton.presentation.screens.main

import cz.levinzonr.spoton.domain.interactors.ShouldShowUpdateReminderInteractor
import cz.levinzonr.spoton.presentation.base.BaseViewModel
import cz.levinzonr.spoton.presentation.extensions.isSuccess
import cz.levinzonr.spoton.presentation.util.SingleEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainViewModel(
        private val shouldShowUpdateReminderInteractor: ShouldShowUpdateReminderInteractor
) : BaseViewModel<Action, Change, State>() {

    override val initialState: State = State()
    override val reducer: suspend (state: State, change: Change) -> State =  {state, change ->
        when(change) {
            is Change.UpdateStateLoaded -> if (change.needUpdate)  {
                state.copy(updateReminderDialog = SingleEvent(Unit))
            } else state
        }
    }

    init {
        startActionsObserver()
        dispatch(Action.Init)
    }

    override fun emitAction(action: Action): Flow<Change> {
        return when (action) {
            is Action.Init -> bindInitAction()
        }
    }

    private fun bindInitAction(): Flow<Change> = flow {
        shouldShowUpdateReminderInteractor()
                .isSuccess { emit(Change.UpdateStateLoaded(it)) }
    }

}