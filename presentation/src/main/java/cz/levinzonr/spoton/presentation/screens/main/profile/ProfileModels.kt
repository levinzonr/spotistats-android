package cz.levinzonr.spoton.presentation.screens.main.profile

import com.spotify.protocol.types.PlayerState
import cz.levinzonr.roxie.BaseAction
import cz.levinzonr.roxie.BaseChange
import cz.levinzonr.roxie.BaseState
import cz.levinzonr.spoton.domain.models.RemotePlayerState
import cz.levinzonr.spoton.domain.models.UserProfile
import cz.levinzonr.spoton.models.PlaylistResponse
import cz.levinzonr.spoton.models.TrackResponse
import cz.levinzonr.spoton.presentation.navigation.Route


sealed class Action : BaseAction {
    object Init : Action()
    object SettingsPressed: Action()
    data class PlaylistPlayButtonPressed(val playlist: PlaylistResponse, val shuffled: Boolean) : Action()
}


sealed class Change : BaseChange {
    object ProfileLoading: Change()

    data class Navigation(val route: Route): Change()
    data class ProfileLoaded(val user: UserProfile) : Change()
    data class ProfileLoadingError(val throwable: Throwable) : Change()

    data class RecentPlaylistsLoded(val playlists: List<PlaylistResponse>) : Change()
    data class RecentPlaylistsError(val throwable: Throwable) : Change()




}


data class State(
        val isLoading: Boolean = false,
        val currentTrack: TrackResponse? = null,
        val recentPlaylists: List<PlaylistResponse> = listOf(),
        val user: UserProfile? = null
) : BaseState