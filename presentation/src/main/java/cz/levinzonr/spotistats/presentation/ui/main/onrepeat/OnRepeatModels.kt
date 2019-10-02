package cz.levinzonr.spotistats.presentation.ui.main.onrepeat

import com.ww.roxie.BaseAction
import com.ww.roxie.BaseChange
import com.ww.roxie.BaseState
import cz.levinzonr.spotistats.models.Item
import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.presentation.util.SingleEvent
import cz.levinzonr.spotistats.presentation.util.ViewError


sealed class Action : BaseAction {
    object Init: Action()
}


sealed class Change: BaseChange {
    object LoadingStarted: Change()
    data class TracksLoaded(val items: List<Item>) : Change()
    data class TracksLoadingError(val throwable: Throwable) : Change()
}


data class State(
        val isLoading: Boolean = false,
        val error: SingleEvent<ViewError>? = null,
        val items: List<Item> = listOf()
) : BaseState