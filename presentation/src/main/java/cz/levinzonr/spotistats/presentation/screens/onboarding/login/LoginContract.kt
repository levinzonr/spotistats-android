package cz.levinzonr.spotistats.presentation.screens.onboarding.login

import android.content.Intent
import androidx.fragment.app.Fragment
import com.ww.roxie.BaseAction
import com.ww.roxie.BaseChange
import com.ww.roxie.BaseState

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