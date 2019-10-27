package cz.levinzonr.spotistats.presentation.screens.main.player


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.api.load
import com.spotify.protocol.types.PlayerState
import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.presentation.R
import cz.levinzonr.spotistats.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_player.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class PlayerFragment : BaseFragment<State>() {

    override val viewModel: PlayerViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false)
    }


    override fun renderState(state: State) {
        Timber.d("Plyerastate: $state")
        state.playerState?.let(this::renderPlayerState)
        state.currentTrack?.let(this::renderTrackDetails)
    }

    private fun renderPlayerState(state: PlayerState) {
        val imageRes = if (state.isPaused) R.drawable.ic_play_arrow else R.drawable.ic_pause_black
        trackPlayBtn.setImageResource(imageRes)
        trackArtistTv.text = state.track.name
        trackNameTv.text = state.track.artist.name


    }


    private fun renderTrackDetails(trackResponse: TrackResponse) {
        trackArtistTv.text = trackResponse.artists.first().name
        trackNameTv.text = trackResponse.name
        trackImageIv.load(trackResponse.album.images.firstOrNull()?.url)
    }

}