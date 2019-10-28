package cz.levinzonr.spoton.domain.models

import com.spotify.protocol.types.PlayerState

sealed class RemotePlayerState {
    object Initilizing: RemotePlayerState()
    data class Ready(val state: PlayerState): RemotePlayerState()
    data class Error(val throwable: Throwable? = null): RemotePlayerState()
}