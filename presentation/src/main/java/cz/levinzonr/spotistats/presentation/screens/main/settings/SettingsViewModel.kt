package cz.levinzonr.spotistats.presentation.screens.main.settings

import cz.levinzonr.spotistats.models.DarkMode
import cz.levinzonr.spotistats.presentation.base.BaseViewModel
import cz.levinzonr.spotistats.presentation.util.SingleEvent
import cz.levinzonr.spotistats.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SettingsViewModel(
        private val settingsRepository: SettingsRepository
) : BaseViewModel<Action, Change, State>() {


    override val initialState: State = State(settingsRepository.darkModeState)


    override val reducer: suspend (state: State, change: Change) -> State = { state, change ->
        when(change) {
            is Change.SettingsUpdated -> state.copy(darkMode = settingsRepository.darkModeState)
            is Change.ShowDialog -> state.copy(showDarkModeDialog = SingleEvent(change.darkMode))
        }
    }

    init {
        startActionsObserver()
    }

    override fun emitAction(action: Action): Flow<Change> {
        return when(action) {
            is Action.DarkModePrefSelected -> bindSetDarkModeAction(action.darkMode)
            is Action.DarkModePreferencePressed -> bindShowDarkModeDialog()
        }
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