package cz.levinzonr.spotistats.presentation.screens.main.profile

import com.ww.roxie.BaseAction
import com.ww.roxie.BaseChange
import com.ww.roxie.BaseState
import cz.levinzonr.spotistats.models.UserResponse
import cz.levinzonr.spotistats.presentation.navigation.Route


sealed class Action : BaseAction {
    object Init : Action()
    object LogoutPressed : Action()
    object SettingsPressed: Action()
}


sealed class Change : BaseChange {
    object LogoutStarted : Change()
    object LogoutFinished : Change()
    object ProfileLoading: Change()
    data class Navigation(val route: Route): Change()
    data class ProfileLoaded(val user: UserResponse) : Change()
    data class ProfileLoadingError(val throwable: Throwable) : Change()

}


data class State(
        val isLoading: Boolean = false,
        val user: UserResponse? = null
) : BaseState