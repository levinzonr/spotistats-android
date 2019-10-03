package cz.levinzonr.spotistats.presentation.screens.main.profile

import cz.levinzonr.spotistats.domain.interactors.GetUserProfileInteractor
import cz.levinzonr.spotistats.domain.managers.UserManager
import cz.levinzonr.spotistats.presentation.base.BaseViewModel
import cz.levinzonr.spotistats.presentation.extensions.flowOnIO
import cz.levinzonr.spotistats.presentation.extensions.isError
import cz.levinzonr.spotistats.presentation.extensions.isSuccess
import cz.levinzonr.spotistats.presentation.navigation.Route
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileViewModel(
        private val getUserProfileInteractor: GetUserProfileInteractor,
        private val userManager: UserManager) : BaseViewModel<Action, Change, State> (){

    override val initialState: State = State()

    override val reducer: suspend (state: State, change: Change) -> State = {state, change ->
        when(change) {
            is Change.LogoutStarted -> state.copy(isLoading = true)
            is Change.LogoutFinished -> state.copy(isLoading = false)
            is Change.ProfileLoading -> state.copy(isLoading = true)
            is Change.ProfileLoaded -> state.copy(isLoading = false, user = change.user)
            is Change.ProfileLoadingError -> state.copy(isLoading = false)

            is Change.Navigation -> state.also { navigateTo(change.route) }
        }
    }

    init {
        startActionsObserver()
        dispatch(Action.Init)
    }

    override fun emitAction(action: Action): Flow<Change> {
        return when(action) {
            is Action.LogoutPressed -> bindLogoutAction()
            is Action.Init -> bindInitAction()
            is Action.SettingsPressed -> bindSettingsClickActions()
        }
    }

    private fun bindLogoutAction() : Flow<Change> = flowOnIO {
        emit(Change.LogoutStarted)
        userManager.logout()
        emit(Change.LogoutFinished)
        emit(Change.Navigation(Route.Onboarding))
    }

    private fun bindInitAction() : Flow<Change> = flowOnIO {
        emit(Change.ProfileLoading)
        getUserProfileInteractor()
                .isError { emit(Change.ProfileLoadingError(it)) }
                .isSuccess { emit(Change.ProfileLoaded(it)) }

    }

    private fun bindSettingsClickActions() : Flow<Change> = flow {
        val route = Route.Destination(ProfileFragmentDirections.actionProfileFragmentToSettingsFragment())
        emit(Change.Navigation(route))
    }
}