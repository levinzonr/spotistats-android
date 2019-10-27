package cz.levinzonr.spotistats.presentation.screens.main.player

import com.spotify.protocol.types.PlayerState
import com.ww.roxie.BaseAction
import com.ww.roxie.BaseChange
import com.ww.roxie.BaseState
import cz.levinzonr.spotistats.domain.models.RemotePlayerState
import cz.levinzonr.spotistats.models.TrackResponse


sealed class Action : BaseAction {
    data class RemotePlayerStateUpdated(val remotePlayerState: RemotePlayerState) : Action()
    object PlayTrackPressed: Action()
    object NextTrackPressed: Action()
    object PreviousTrackPressed: Action()
}

sealed class Change : BaseChange {
    data class TrackDetailsLoaded(val trackResponse: TrackResponse) : Change()
    data class RemotePlayerReady(val state: PlayerState) : Change()
    object RemotePlayerReading : Change()
    data class RemotePlayerError(val throwable: Throwable):  Change()
}

data class State(
        val currentTrack: TrackResponse? = null,
        val playerState: PlayerState? = null,
        val error: Throwable? = null) : BaseState