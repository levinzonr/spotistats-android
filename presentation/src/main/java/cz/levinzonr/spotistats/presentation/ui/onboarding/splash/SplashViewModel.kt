package cz.levinzonr.spotistats.presentation.ui.onboarding.splash

import cz.levinzonr.spotistats.domain.managers.UserManager
import cz.levinzonr.spotistats.presentation.base.BaseViewModel
import cz.levinzonr.spotistats.presentation.navigation.Route
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class SplashViewModel(private val userManager: UserManager) : BaseViewModel<Action, Change, State>() {


    override val initialState: State = State(false)


    override val reducer: suspend (state: State, change: Change) -> State = {state, change ->
        when(change) {
            is Change.InitStarted -> state.copy(isLoading = true)
            is Change.InitFinished -> state.copy(isLoading = false).also {
                if (change.showMain) navigateTo(Route.Main) else navigateToLogin()
            }
        }
    }

    init {
        startActionsObserver()
    }


    override fun emitAction(action: Action): Flow<Change> {
       return when(action) {
           is Action.Init -> bindInitAction()
       }
    }

    private fun navigateToLogin() {
        val dest = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
        navigateTo(Route.Destination(dest))
    }

    private fun bindInitAction() : Flow<Change> = flow {
        Timber.d("State")
        emit(Change.InitStarted)
        Timber.d("Emited")
        delay(1000)
        Timber.d("Delayed")
        emit(Change.InitFinished(userManager.isLoggedIn()))
        Timber.d("Emited 2")
    }
}