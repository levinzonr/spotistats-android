package cz.levinzonr.spotistats.presentation.ui.onboarding.login

import com.ww.roxie.BaseAction
import com.ww.roxie.BaseChange
import com.ww.roxie.BaseState

data class State(val isLoading: Boolean) : BaseState

sealed class Change: BaseChange {
    object TMP : Change()
}

sealed class Action : BaseAction {
    object LoginClicked: Action()
}