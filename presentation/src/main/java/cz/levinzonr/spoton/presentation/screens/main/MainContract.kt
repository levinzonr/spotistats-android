package cz.levinzonr.spoton.presentation.screens.main

import cz.levinzonr.roxie.BaseAction
import cz.levinzonr.roxie.BaseChange
import cz.levinzonr.roxie.BaseState
import cz.levinzonr.spoton.presentation.util.SingleEvent

data class State(
        val errorMessage: SingleEvent<String>? = null,
        val isLoading: Boolean = false,
        val updateReminderDialog: SingleEvent<Unit>? = null
) : BaseState

sealed class Change: BaseChange {
    data class UpdateStateLoaded(val needUpdate: Boolean) : Change()
}

sealed class Action: BaseAction {
    object Init: Action()
}