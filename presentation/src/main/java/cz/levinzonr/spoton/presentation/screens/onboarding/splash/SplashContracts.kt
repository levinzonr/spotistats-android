package cz.levinzonr.spoton.presentation.screens.onboarding.splash

import com.ww.roxie.BaseAction
import com.ww.roxie.BaseChange
import com.ww.roxie.BaseState


sealed class Action : BaseAction {
    object Init: Action()
}

sealed class Change : BaseChange {
    object InitStarted : Change()
    data class InitFinished(val showMain: Boolean) : Change()
}

data class State(val isLoading: Boolean = true) : BaseState