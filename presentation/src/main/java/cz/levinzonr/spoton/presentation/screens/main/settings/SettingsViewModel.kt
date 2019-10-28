package cz.levinzonr.spoton.presentation.screens.main.settings

import cz.levinzonr.spoton.domain.managers.UserManager
import cz.levinzonr.spoton.models.DarkMode
import cz.levinzonr.spoton.presentation.base.BaseViewModel
import cz.levinzonr.spoton.presentation.navigation.Route
import cz.levinzonr.spoton.presentation.util.SingleEvent
import cz.levinzonr.spoton.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SettingsViewModel(
        private val userManager: UserManager,
        private val settingsRepository: SettingsRepository
) : BaseViewModel<Action, Change, State>() {


    override val initialState: State = State(settingsRepository.darkModeState)


    override val reducer: suspend (state: State, change: Change) -> State = { state, change ->
        when(change) {
            is Change.SettingsUpdated -> state.copy(darkMode = settingsRepository.darkModeState)
            is Change.ShowDialog -> state.copy(showDarkModeDialog = SingleEvent(change.darkMode))
            is Change.Navigation -> state.copy().also { navigateTo(change.route) }
        }
    }

    init {
        startActionsObserver()
    }

    override fun emitAction(action: Action): Flow<Change> {
        return when(action) {
            is Action.DarkModePrefSelected -> bindSetDarkModeAction(action.darkMode)
            is Action.DarkModePreferencePressed -> bindShowDarkModeDialog()
            is Action.LogoutButtonClicked -> bindLogoutAction()
            is Action.AboutButtonClicked -> flow {  }
            is Action.FeedbackButtonClicked -> flow {  }
        }
    }

    private fun bindLogoutAction() : Flow<Change> = flow {
        userManager.logout()
        emit(Change.Navigation(Route.Onboarding))
    }

    private fun bindSetDarkModeAction(darkMode: DarkMode) : Flow<Change> = flow {
        settingsRepository.darkModeState = darkMode
        emit(Change.SettingsUpdated)
    }

    private fun bindShowDarkModeDialog() : Flow<Change> = flow {
        val current = settingsRepository.darkModeState
        emit(Change.ShowDialog(current))
    }
}