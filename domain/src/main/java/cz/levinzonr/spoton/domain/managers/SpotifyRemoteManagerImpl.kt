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
import cz.levinzonr.spoton.domain.models.PlayerActionResult
import cz.levinzonr.spoton.domain.models.RemotePlayerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SpotifyRemoteManagerImpl(
        private val context: Context,
        private val params: ConnectionParams
) : SpotifyRemoteManager, Connector.ConnectionListener {


    private fun launchOnIO(block: () -> Unit) = GlobalScope.launch {
        withContext(Dispatchers.IO) {
            block.invoke()
        }
    }

    private fun withPlayerApi(block: (playerApi: PlayerApi) -> PlayerActionResult): PlayerActionResult {
        return this.appRemote?.playerApi?.let(block)
                ?: PlayerActionResult.Error(Exception(), "Init error")
    }

    private val _stateLiveData = MutableLiveData<RemotePlayerState>()

    override val stateLiveData: LiveData<RemotePlayerState>
        get() = _stateLiveData

    init {
        _stateLiveData.postValue(RemotePlayerState.Initilizing)
        SpotifyAppRemote.connect(context, params, this)
    }

    private var appRemote: SpotifyAppRemote? = null


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


    override suspend fun play(trackId: String): PlayerActionResult {
        return withPlayerApi { it.play(trackId).mapToResult() }
    }

    override suspend fun addToQueue(trackId: String) = withPlayerApi { playerApi ->
        playerApi.queue(trackId).mapToResult()
    }


    override suspend fun next(): PlayerActionResult {
        return withPlayerApi { it.skipNext().mapToResult() }
    }

    override suspend fun pause(): PlayerActionResult {
        return withPlayerApi { it.pause().mapToResult() }
    }

    override suspend fun toggle(): PlayerActionResult {
        return withPlayerApi {
            val currentState = it.playerState.mapToResult()
            if (currentState is PlayerActionResult.Success) {
                if (it.playerState.await().data.isPaused) {
                    it.resume().mapToResult()
                } else {
                    it.pause().mapToResult()
                }
            } else {
                return@withPlayerApi currentState
            }

        }
    }

    override suspend fun previous(): PlayerActionResult {
        return withPlayerApi { it.skipPrevious().mapToResult() }
    }

    override suspend fun shuffle(shuffle: Boolean): PlayerActionResult {
        return withPlayerApi { it.setShuffle(shuffle).mapToResult() }
    }

    private fun CallResult<*>.mapToResult(): PlayerActionResult {
        val result = await()
        return when {
            result.isSuccessful && result.data != null -> PlayerActionResult.Success
            else -> PlayerActionResult.Error(result.error, result.errorMessage)
        }
    }


}