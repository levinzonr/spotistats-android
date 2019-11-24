package cz.levinzonr.spoton.presentation.screens.onboarding.splash

import cz.levinzonr.roxie.BaseAction
import cz.levinzonr.roxie.BaseChange
import cz.levinzonr.roxie.BaseState


sealed class Action : BaseAction {
    object Init: Action()
}

sealed class Change : BaseChange {
    object InitStarted : Change()
    data class InitFinished(val showMain: Boolean) : Change()
}

data class State(val isLoading: Boolean = true) : BaseState