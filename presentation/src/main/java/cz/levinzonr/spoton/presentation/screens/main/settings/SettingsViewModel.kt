package cz.levinzonr.spoton.presentation.screens.main.settings

import cz.levinzonr.spoton.domain.interactors.GetDeviceInfoInteractor
import cz.levinzonr.spoton.domain.managers.AppConfig
import cz.levinzonr.spoton.domain.managers.UserManager
import cz.levinzonr.spoton.models.DarkMode
import cz.levinzonr.spoton.presentation.base.BaseViewModel
import cz.levinzonr.spoton.presentation.extensions.flowOnIO
import cz.levinzonr.spoton.presentation.extensions.isSuccess
import cz.levinzonr.spoton.presentation.navigation.Route
import cz.levinzonr.spoton.presentation.util.SingleEvent
import cz.levinzonr.spoton.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SettingsViewModel(
        private val userManager: UserManager,
        private val settingsRepository: SettingsRepository,
        private val appConfig: AppConfig,
        private val getDeviceInfoInteractor: GetDeviceInfoInteractor

) : BaseViewModel<Action, Change, State>() {


    override val initialState: State = State(
            darkMode = settingsRepository.darkModeState,
            versionName = "Version ${appConfig.versionName} (${appConfig.versionCode})"
    )


    override val reducer: suspend (state: State, change: Change) -> State = { state, change ->
        when(change) {
            is Change.SettingsUpdated -> state.copy(darkMode = settingsRepository.darkModeState)
            is Change.ShowDialog -> state.copy(showDarkModeDialog = SingleEvent(change.darkMode))
            is Change.Navigation -> state.copy().also { navigateTo(change.route) }
            is Change.DeviceInfoLoaded -> state.copy(showFeedbackView = SingleEvent(change.deviceInfo))
            is Change.OpenBrowser -> state.copy(openBrowser = SingleEvent(change.url))
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
            is Action.AboutButtonClicked -> flow { emit(Change.OpenBrowser("https://github.com/levinzonr/spotistats-android")) }
            is Action.FeedbackButtonClicked -> bindFeebackButtonAction()
        }
    }

    private fun bindFeebackButtonAction() : Flow<Change> = flowOnIO {
        getDeviceInfoInteractor.invoke()
                .isSuccess { emit(Change.DeviceInfoLoaded(it)) }
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