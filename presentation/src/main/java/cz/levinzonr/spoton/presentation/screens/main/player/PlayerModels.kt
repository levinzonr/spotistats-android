package cz.levinzonr.spoton.presentation.screens.main.player

import com.spotify.protocol.types.PlayerState
import cz.levinzonr.roxie.BaseAction
import cz.levinzonr.roxie.BaseChange
import cz.levinzonr.roxie.BaseState
import cz.levinzonr.spoton.domain.models.RemotePlayerState
import cz.levinzonr.spoton.models.TrackResponse
import cz.levinzonr.spoton.presentation.navigation.Route
import cz.levinzonr.spoton.presentation.util.SingleEvent
import io.reactivex.Single


sealed class Action : BaseAction {
    data class RemotePlayerStateUpdated(val remotePlayerState: RemotePlayerState) : Action()
    object PlayTrackPressed: Action()
    object NextTrackPressed: Action()
    object PreviousTrackPressed: Action()
    object RetryConnectionPressed: Action()
    data class PlayerTrackActionPressed(val trackId: String): Action()
}

sealed class Change : BaseChange {
    data class TrackDetailsLoaded(val trackResponse: TrackResponse) : Change()
    data class RemotePlayerReady(val state: PlayerState) : Change()
    object RemotePlayerReading : Change()
    data class Navigation(val route: Route) : Change()
    data class RemotePlayerError(val throwable: Throwable):  Change()
    data class PlayerActionSuccess(val message: String) : Change()
    data class PlayerActionError(val throwable: Throwable) : Change()
}

data class State(
        val remotePlayerError: String? = null,
        val currentTrack: TrackResponse? = null,
        val playerState: PlayerState? = null,
        val isLoading: Boolean = false,
        val toast: SingleEvent<String>? = null,
        val snackbar: SingleEvent<String>? = null,
        val error: Throwable? = null) : BaseState