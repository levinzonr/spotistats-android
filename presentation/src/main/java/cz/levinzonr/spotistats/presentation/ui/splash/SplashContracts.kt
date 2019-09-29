package cz.levinzonr.spotistats.presentation.ui.splash

import com.ww.roxie.BaseAction
import com.ww.roxie.BaseChange
import com.ww.roxie.BaseState


sealed class Action : BaseAction
sealed class Change : BaseChange {
    object One: Change()
}
data class State(
        val doneLoading: Boolean) : BaseState