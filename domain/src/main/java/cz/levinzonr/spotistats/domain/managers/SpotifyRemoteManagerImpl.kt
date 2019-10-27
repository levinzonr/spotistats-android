package cz.levinzonr.spotistats.domain.managers

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.PlayerApi
import com.spotify.android.appremote.api.SpotifyAppRemote
import cz.levinzonr.spotistats.domain.extensions.guard
import cz.levinzonr.spotistats.domain.models.RemotePlayerState
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

    private fun withPlayerApi(block: (playerApi: PlayerApi) -> Unit) {
        this.appRemote?.playerApi?.let(block)
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
    }


    override fun next() = withPlayerApi { playerApi ->
        launchOnIO { playerApi.skipNext().await() }
    }


    override fun pause() = withPlayerApi { playerApi ->
        launchOnIO { playerApi.pause().await() }
    }

    override fun play(trackId: String) = withPlayerApi { playerApi ->
        playerApi.play(trackId)

    }

    override fun addToQueue(trackId: String) = withPlayerApi { playerApi ->
        playerApi.queue(trackId)

    }


    override fun previous() = withPlayerApi { playerApi ->
        launchOnIO { playerApi.skipPrevious() }
    }

    override fun shuffle(shuffle: Boolean) = withPlayerApi { playerApi ->
        playerApi.setShuffle(shuffle)
    }

    override fun toggle() = withPlayerApi { playerApi ->
        launchOnIO {
            val state = playerApi.playerState.await()
            if (state.isSuccessful) {
                if (state.data.isPaused) {
                    playerApi.resume().await()
                } else {
                    playerApi.pause().await()
                }
            }
        }
    }


}