package cz.levinzonr.spotistats.presentation.screens.main.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.api.load
import coil.transform.CircleCropTransformation
import com.spotify.protocol.types.PlayerState
import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.models.UserResponse
import cz.levinzonr.spotistats.presentation.R
import cz.levinzonr.spotistats.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : BaseFragment<State>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override val viewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userLogoutBtn.setOnClickListener { viewModel.dispatch(Action.LogoutPressed) }
        settingsBtn.setOnClickListener { viewModel.dispatch(Action.SettingsPressed) }
        trackPlayBtn.setOnClickListener { viewModel.dispatch(Action.PlayTrackPressed) }
        trackNextBtn.setOnClickListener { viewModel.dispatch(Action.NextTrackPressed) }
        trackPreviousBtn.setOnClickListener { viewModel.dispatch(Action.PreviousTrackPressed) }
    }

    override fun renderState(state: State) {
        userLogoutBtn.isEnabled = !state.isLoading
        state.user?.let(this::renderProfile)
        state.playerState?.let(this::renderPlayerState)
        state.currentTrack?.let(this::renderTrackDetails)
    }

    private fun renderProfile(user: UserResponse) {
        userDisplayNameTv.text = user.display_name
        userAvaterIv.load(user.images.firstOrNull()?.url) {
            transformations(CircleCropTransformation())
        }
    }

    private fun renderPlayerState(playerState: PlayerState) {
        val imageRes = if (playerState.isPaused) R.drawable.ic_play_arrow else R.drawable.ic_pause_black
        trackPlayBtn.setImageResource(imageRes)
        trackArtistTv.text = playerState.track.name
        trackNameTv.text = playerState.track.artist.name

    }

    private fun renderTrackDetails(trackResponse: TrackResponse) {
        Timber.d("Render Tracl: $trackResponse")
        trackArtistTv.text = trackResponse.artists.first().name
        trackNameTv.text = trackResponse.name
        trackImageIv.load(trackResponse.album.images.firstOrNull()?.url) {
            transformations(CircleCropTransformation())
        }
    }
}
