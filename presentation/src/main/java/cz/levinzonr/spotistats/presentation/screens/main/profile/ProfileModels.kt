package cz.levinzonr.spotistats.presentation.screens.main.profile

import com.ww.roxie.BaseAction
import com.ww.roxie.BaseChange
import com.ww.roxie.BaseState


sealed class Action : BaseAction {
    object LogoutPressed : Action()
}


sealed class Change : BaseChange {
    object LogoutStarted : Change()
    object LogoutFinished: Change()

}


data class State(val isLoading: Boolean = false) : BaseState