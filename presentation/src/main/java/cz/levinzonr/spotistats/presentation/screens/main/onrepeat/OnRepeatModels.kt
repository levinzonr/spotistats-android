package cz.levinzonr.spotistats.presentation.screens.main.onrepeat

import com.ww.roxie.BaseAction
import com.ww.roxie.BaseChange
import com.ww.roxie.BaseState
import cz.levinzonr.spotistats.domain.Tracks
import cz.levinzonr.spotistats.presentation.util.SingleEvent
import cz.levinzonr.spotistats.presentation.util.ViewError


sealed class Action : BaseAction {
    object Init: Action()
}


sealed class Change: BaseChange {
    object LoadingStarted: Change()
    data class TracksLoaded(val items: Tracks) : Change()
    data class TracksLoadingError(val throwable: Throwable) : Change()
}


data class State(
        val isLoading: Boolean = false,
        val error: SingleEvent<ViewError>? = null,
        val tracks: Tracks? = null
) : BaseState


