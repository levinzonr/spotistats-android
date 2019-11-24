package cz.levinzonr.spoton.presentation.screens.onboarding.login

import android.content.Intent
import androidx.fragment.app.Fragment
import cz.levinzonr.roxie.BaseAction
import cz.levinzonr.roxie.BaseChange
import cz.levinzonr.roxie.BaseState

data class State(val isLoading: Boolean) : BaseState

sealed class Change: BaseChange {
    object LoginStarted : Change()
    object LoginSuccess : Change()
    object LoginFailed  : Change()
}

sealed class Action : BaseAction {
    data class LoginClicked(val fragment: Fragment) : Action()
    data class LoginResult(val requestCode: Int, val resultCode: Int, val data: Intent?) : Action()
}