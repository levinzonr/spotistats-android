package cz.levinzonr.spoton.domain.managers

import androidx.lifecycle.LiveData
import cz.levinzonr.spoton.domain.models.RemotePlayerState

interface SpotifyRemoteManager {

    val stateLiveData: LiveData<RemotePlayerState>
    suspend fun play(trackId: String)
    suspend fun next()
    suspend fun pause()
    suspend fun toggle()
    suspend fun previous()
    suspend fun addToQueue(trackId: String)
    suspend fun shuffle(shuffle: Boolean)
    fun connect()
    fun disconnect()
}