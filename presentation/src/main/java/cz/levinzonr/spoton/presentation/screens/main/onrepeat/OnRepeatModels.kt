package cz.levinzonr.spoton.presentation.screens.main.onrepeat

import cz.levinzonr.roxie.BaseAction
import cz.levinzonr.roxie.BaseChange
import cz.levinzonr.roxie.BaseState
import cz.levinzonr.spoton.domain.models.Tracks
import cz.levinzonr.spoton.models.TrackResponse
import cz.levinzonr.spoton.presentation.navigation.Route
import cz.levinzonr.spoton.presentation.util.SingleEvent
import cz.levinzonr.spoton.presentation.util.ViewError


sealed class Action : BaseAction {
    object Init: Action()
    data class TrackClicked(val track: TrackResponse) : Action()
    data class CreatePlaylistAction(val tracks: List<TrackResponse>, val playlistName: String) : Action()
    data class AddToPlaylistAction(val tracks: List<TrackResponse>) : Action()
}


sealed class Change: BaseChange {
    object LoadingStarted: Change()

    data class Navigation(val route: Route) : Change()
    data class TracksLoaded(val items: Tracks) : Change()
    data class TracksLoadingError(val throwable: Throwable) : Change()
    object PlaylistCreated: Change()
}


data class State(
        val isLoading: Boolean = false,
        val error: SingleEvent<ViewError>? = null,
        val playlistCreated: SingleEvent<Unit>? = null,
        val tracks: Tracks? = null
) : BaseState


