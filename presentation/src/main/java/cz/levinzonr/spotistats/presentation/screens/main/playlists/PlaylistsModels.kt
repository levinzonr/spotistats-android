package cz.levinzonr.spotistats.presentation.screens.main.playlists

import com.ww.roxie.BaseAction
import com.ww.roxie.BaseChange
import com.ww.roxie.BaseState
import cz.levinzonr.spotistats.models.PlaylistResponse
import cz.levinzonr.spotistats.presentation.util.SingleEvent
import cz.levinzonr.spotistats.presentation.util.ViewError

sealed class Action : BaseAction {
    object Init: Action()
    data class PlaylistClicked(val playlistResponse: PlaylistResponse, val force: Boolean = false) : Action()
}

sealed class Change : BaseChange {
    object PlaylistsLoading: Change()
    data class PlaylistsLoaded(val playlists: List<PlaylistResponse>) : Change()
    data class PlaylistsLoadingError(val throwable: Throwable) : Change()
    data class TrackAlreadyAdded(val playlistResponse: PlaylistResponse): Change()
    object TrackAdded: Change()
    data class TrackAdditionError(val throws: Throwable) : Change()
}

data class State (
        val isLoading: Boolean = false,
        val error: SingleEvent<ViewError>? = null,
        val playlists: List<PlaylistResponse> = listOf(),
        val duplicateDialog: SingleEvent<PlaylistResponse>? = null,
        val successEvent: SingleEvent<Unit>? = null
) : BaseState