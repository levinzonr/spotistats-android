package cz.levinzonr.spotistats.presentation.ui.main.profile

import cz.levinzonr.spotistats.domain.managers.UserManager
import cz.levinzonr.spotistats.presentation.base.BaseViewModel
import cz.levinzonr.spotistats.presentation.extensions.flowOnIO
import cz.levinzonr.spotistats.presentation.navigation.Route
import kotlinx.coroutines.flow.Flow

class ProfileViewModel(private val userManager: UserManager) : BaseViewModel<Action, Change, State> (){

    override val initialState: State = State()

    override val reducer: suspend (state: State, change: Change) -> State = {state, change ->
        when(change) {
            Change.LogoutStarted -> state.copy(isLoading = true)
            Change.LogoutFinished -> state.copy(isLoading = false).also {
                navigateTo(Route.Onboarding)
            }
        }
    }

    init {
        startActionsObserver()
    }

    override fun emitAction(action: Action): Flow<Change> {
        return when(action) {
            Action.LogoutPressed -> bindLogoutAction()
        }
    }

    private fun bindLogoutAction() : Flow<Change> = flowOnIO {
        emit(Change.LogoutStarted)
        userManager.logout()
        emit(Change.LogoutFinished)
    }
}