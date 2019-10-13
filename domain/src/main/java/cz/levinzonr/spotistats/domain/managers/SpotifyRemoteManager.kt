package cz.levinzonr.spotistats.domain.managers

import androidx.lifecycle.LiveData
import cz.levinzonr.spotistats.domain.models.RemotePlayerState

interface SpotifyRemoteManager {

    val stateLiveData: LiveData<RemotePlayerState>

    fun play(trackId: String)
    fun addToQueue(trackId: String)

}