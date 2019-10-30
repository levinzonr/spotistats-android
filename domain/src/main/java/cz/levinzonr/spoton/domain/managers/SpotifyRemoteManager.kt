package cz.levinzonr.spoton.domain.managers

import androidx.lifecycle.LiveData
import cz.levinzonr.spoton.domain.models.PlayerActionResult
import cz.levinzonr.spoton.domain.models.RemotePlayerState

interface SpotifyRemoteManager {

    val stateLiveData: LiveData<RemotePlayerState>

    suspend fun play(trackId: String) : PlayerActionResult
    suspend fun next() : PlayerActionResult
    suspend fun pause() : PlayerActionResult
    suspend fun toggle() : PlayerActionResult
    suspend fun previous() : PlayerActionResult
    suspend fun addToQueue(trackId: String) : PlayerActionResult
    suspend fun shuffle(shuffle: Boolean) : PlayerActionResult
}