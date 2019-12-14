package cz.levinzonr.spoton.presentation.screens.main.settings

import cz.levinzonr.roxie.BaseAction
import cz.levinzonr.roxie.BaseChange
import cz.levinzonr.roxie.BaseState
import cz.levinzonr.spoton.models.DarkMode
import cz.levinzonr.spoton.presentation.navigation.Route
import cz.levinzonr.spoton.presentation.util.SingleEvent


sealed class Action: BaseAction {
    data class DarkModePrefSelected(val darkMode: DarkMode) : Action()
    object DarkModePreferencePressed : Action()
    object AboutButtonClicked: Action()
    object FeedbackButtonClicked: Action()
    object LogoutButtonClicked: Action()

}

sealed class Change: BaseChange {
    object SettingsUpdated: Change()
    data class Navigation(val route: Route): Change()
    data class ShowDialog(val darkMode: DarkMode) : Change()
}

data class State(
        val darkMode: DarkMode,
        val versionName: String,
        val showDarkModeDialog: SingleEvent<DarkMode>? = null) : BaseState