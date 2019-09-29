package cz.levinzonr.spotistats.presentation.ui.onboarding.login

import cz.levinzonr.spotistats.domain.managers.UserManager
import cz.levinzonr.spotistats.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class LoginViewModel(
        private val userManager: UserManager
) : BaseViewModel<Action, Change, State>() {

    override val initialState: State = State(false)

    override val reducer: suspend (state: State, change: Change) -> State = { state, change ->
        state.copy(false)
    }

    override fun emitAction(action: Action): Flow<Change> {
        return flowOf()
    }
}