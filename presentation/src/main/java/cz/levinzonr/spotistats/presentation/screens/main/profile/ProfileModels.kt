package cz.levinzonr.spotistats.presentation.screens.main.profile

import com.spotify.protocol.types.PlayerState
import com.ww.roxie.BaseAction
import com.ww.roxie.BaseChange
import com.ww.roxie.BaseState
import cz.levinzonr.spotistats.domain.models.RemotePlayerState
import cz.levinzonr.spotistats.domain.models.UserProfile
import cz.levinzonr.spotistats.models.PlaylistResponse
import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.presentation.navigation.Route


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