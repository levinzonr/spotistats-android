package cz.levinzonr.spotistats.presentation.screens.main.profile

import com.spotify.protocol.types.PlayerState
import com.ww.roxie.BaseAction
import com.ww.roxie.BaseChange
import com.ww.roxie.BaseState
import cz.levinzonr.spotistats.domain.models.RemotePlayerState
import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.models.UserResponse
import cz.levinzonr.spotistats.presentation.navigation.Route


sealed class Action : BaseAction {
    object Init : Action()
    object LogoutPressed : Action()
    object SettingsPressed: Action()
    data class RemotePlayerStateUpdated(val remotePlayerState: RemotePlayerState) : Action()
    object PlayTrackPressed: Action()
    object NextTrackPressed: Action()
    object PreviousTrackPressed: Action()
}


sealed class Change : BaseChange {
    object LogoutStarted : Change()
    object LogoutFinished : Change()
    object ProfileLoading: Change()

    data class Navigation(val route: Route): Change()
    data class ProfileLoaded(val user: UserResponse) : Change()
    data class ProfileLoadingError(val throwable: Throwable) : Change()

    data class TrackDetailsLoaded(val trackResponse: TrackResponse) : Change()
    data class RemotePlayerReady(val state: PlayerState) : Change()
    object RemotePlayerReading : Change()
    object RemotePlayerError:  Change()


}


data class State(
        val playerState: PlayerState? = null,
        val isLoading: Boolean = false,
        val currentTrack: TrackResponse? = null,
        val user: UserResponse? = null
) : BaseState