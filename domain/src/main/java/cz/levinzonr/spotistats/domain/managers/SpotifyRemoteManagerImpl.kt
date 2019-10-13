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

    }

    override fun onConnected(remote: SpotifyAppRemote?) {
        appRemote = remote
        appRemote?.playerApi?.let {
            init(it)
        }.guard {
            _stateLiveData.postValue(RemotePlayerState.Error())
        }
    }

    override fun play(trackId: String) {
        appRemote?.playerApi?.let { playerApi ->
            playerApi.play(trackId)
        }
    }


    override fun addToQueue(trackId: String) {
        appRemote?.playerApi?.let { playerApi ->
            playerApi.queue(trackId)
        }
    }

    private fun init(playerApi: PlayerApi) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val result = playerApi.playerState.await()
                if (result.isSuccessful) {
                    _stateLiveData.postValue(RemotePlayerState.Ready(result.data))
                    playerApi.subscribeToPlayerState().setEventCallback {
                        _stateLiveData.postValue(RemotePlayerState.Ready(it))
                    }
                }
            }
        }
    }
}