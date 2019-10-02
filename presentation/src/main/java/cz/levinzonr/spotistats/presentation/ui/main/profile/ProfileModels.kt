package cz.levinzonr.spotistats.presentation.ui.main.profile

import com.ww.roxie.BaseAction
import com.ww.roxie.BaseChange
import com.ww.roxie.BaseState
import cz.levinzonr.spotistats.models.Item
import cz.levinzonr.spotistats.presentation.util.SingleEvent
import cz.levinzonr.spotistats.presentation.util.ViewError


sealed class Action : BaseAction {
    object LogoutPressed : Action()
}


sealed class Change : BaseChange {
    object LogoutStarted : Change()
    object LogoutFinished: Change()

}


data class State(val isLoading: Boolean = false) : BaseState