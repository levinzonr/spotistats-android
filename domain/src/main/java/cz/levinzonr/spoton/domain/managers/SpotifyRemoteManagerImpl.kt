package cz.levinzonr.spoton.domain.managers

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.PlayerApi
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.client.CallResult
import cz.levinzonr.spoton.domain.extensions.guard
import cz.levinzonr.spoton.domain.models.RemotePlayerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SpotifyRemoteManagerImpl(
        private val context: Context,
        private val params: ConnectionParams
) : SpotifyRemoteManager, Connector.ConnectionListener {

    private var appRemote: SpotifyAppRemote? = null

    private fun launchOnIO(block: () -> Unit) = GlobalScope.launch {
        withContext(Dispatchers.IO) {
            block.invoke()
        }
    }

    private fun withPlayerApi(block: (playerApi: PlayerApi) -> CallResult<*>) {
        this.appRemote?.playerApi?.let(block)?.awaitAndValidate()
    }

    private val _stateLiveData = MutableLiveData<RemotePlayerState>()

    override val stateLiveData: LiveData<RemotePlayerState>
        get() = _stateLiveData


    override fun connect() {
        _stateLiveData.postValue(RemotePlayerState.Initilizing)
        SpotifyAppRemote.connect(context, params, this)
    }

    override fun disconnect() {
        appRemote?.let(SpotifyAppRemote::disconnect)
    }



    override fun onFailure(p0: Throwable?) {
        println("Error: $p0")
        _stateLiveData.postValue(RemotePlayerState.Error(p0))

    }

    override fun onConnected(remote: SpotifyAppRemote?) {
        appRemote = remote
        appRemote?.playerApi?.let {
            init(it)
        }.guard {
            _stateLiveData.postValue(RemotePlayerState.Error())
        }
    }


    private fun init(playerApi: PlayerApi) = launchOnIO {
        playerApi.subscribeToPlayerState().setEventCallback {
            if (it.track != null)
                _stateLiveData.postValue(RemotePlayerState.Ready(it))
        }
        playerApi.subscribeToPlayerState().setErrorCallback {
            _stateLiveData.postValue(RemotePlayerState.Error(it))
        }
     }


    override suspend fun play(trackId: String) {
        withPlayerApi { api ->
            val state = api.playerState.await().data
            when {
                state.isPaused && state.track.uri == trackId -> api.resume()
                !state.isPaused && state.track.uri == trackId -> api.pause()
                else -> api.play(trackId)
            }
        }
    }

    override suspend fun next() {
        withPlayerApi { api -> api.skipNext() }
    }

    override suspend fun pause() {
        withPlayerApi { playerApi -> playerApi.pause() }
    }

    override suspend fun toggle() {
        withPlayerApi { playerApi ->
           if (playerApi.playerState.await().data.isPaused) {
               playerApi.resume()
           } else {
               playerApi.pause()
           }
        }
    }

    override suspend fun previous() {
        withPlayerApi { playerApi -> playerApi.skipPrevious() }
    }

    override suspend fun addToQueue(trackId: String) {
        withPlayerApi { playerApi -> playerApi.queue(trackId) }
    }

    override suspend fun shuffle(shuffle: Boolean) {
        withPlayerApi { playerApi -> playerApi.setShuffle(shuffle) }
    }

    private fun CallResult<*>.awaitAndValidate() : Boolean {
        val result = await()
        return when {
            result.isSuccessful && result.data != null -> true
            else -> throw IllegalStateException(result.errorMessage ?: "")
        }
    }


}