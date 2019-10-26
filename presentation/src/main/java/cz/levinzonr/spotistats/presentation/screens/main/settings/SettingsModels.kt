package cz.levinzonr.spotistats.presentation.screens.main.settings

import com.ww.roxie.BaseAction
import com.ww.roxie.BaseChange
import com.ww.roxie.BaseState
import cz.levinzonr.spotistats.models.DarkMode
import cz.levinzonr.spotistats.presentation.navigation.Route
import cz.levinzonr.spotistats.presentation.util.SingleEvent


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
        val showDarkModeDialog: SingleEvent<DarkMode>? = null) : BaseState