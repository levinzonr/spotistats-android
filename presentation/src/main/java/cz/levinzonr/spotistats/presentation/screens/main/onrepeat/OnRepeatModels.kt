package cz.levinzonr.spotistats.presentation.screens.main.onrepeat

import androidx.navigation.NavDirections
import com.ww.roxie.BaseAction
import com.ww.roxie.BaseChange
import com.ww.roxie.BaseState
import cz.levinzonr.spotistats.domain.Tracks
import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.presentation.navigation.Route
import cz.levinzonr.spotistats.presentation.util.SingleEvent
import cz.levinzonr.spotistats.presentation.util.ViewError


sealed class Action : BaseAction {
    object Init: Action()
    data class TrackClicked(val track: TrackResponse) : Action()
}


sealed class Change: BaseChange {
    object LoadingStarted: Change()

    data class Navigation(val route: Route) : Change()
    data class TracksLoaded(val items: Tracks) : Change()
    data class TracksLoadingError(val throwable: Throwable) : Change()
}


data class State(
        val isLoading: Boolean = false,
        val error: SingleEvent<ViewError>? = null,
        val tracks: Tracks? = null
) : BaseState


