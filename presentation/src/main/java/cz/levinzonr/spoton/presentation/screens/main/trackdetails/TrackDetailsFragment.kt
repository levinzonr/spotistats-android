package cz.levinzonr.spoton.presentation.screens.main.trackdetails


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.api.load
import cz.levinzonr.spoton.models.RecommendedTracks
import cz.levinzonr.spoton.models.TrackFeaturesResponse
import cz.levinzonr.spoton.models.TrackResponse
import cz.levinzonr.spoton.models.artists

import cz.levinzonr.spoton.presentation.R
import cz.levinzonr.spoton.presentation.base.BaseFragment
import cz.levinzonr.spoton.presentation.extensions.showToast
import cz.levinzonr.spoton.presentation.extensions.toMmSs
import cz.levinzonr.spoton.presentation.extensions.toPercentageString
import cz.levinzonr.spoton.presentation.screens.main.onrepeat.TrackListAdapter
import cz.levinzonr.spoton.presentation.util.Source
import cz.levinzonr.spoton.presentation.views.setKey
import kotlinx.android.synthetic.main.fragment_track_details.*
import kotlinx.android.synthetic.main.include_track_details.*
import kotlinx.android.synthetic.main.include_track_features.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * A simple [Fragment] subclass.
 */
class TrackDetailsFragment : BaseFragment<State>(), TrackListAdapter.TrackItemListener {

    private val args: TrackDetailsFragmentArgs by navArgs()
    override val viewModel: TrackDetailsViewModel by viewModel { parametersOf(args.trackId) }
    private lateinit var adapter: TrackListAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_track_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TrackListAdapter(this)
        tracksRecommendedRv.adapter = adapter
        remotePlayerPlayBtn.setOnClickListener { viewModel.dispatch(Action.PlayTrackClicked(args.trackId)) }
        remotePlayerQueueBtn.setOnClickListener { viewModel.dispatch(Action.QueueTrackClicked(args.trackId)) }
        remotePlayerPlaylistBtn.setOnClickListener { viewModel.dispatch(Action.AddToPlaylistClicked(args.trackId)) }
    }


    override fun renderState(state: State) {
        renderTrackDetails(state.trackSource)
        renderTrackFeatures(state.featuresSource)
        renderTrackRecommended(state.recommendedSource)
        state.toast?.consume()?.let(this::showToast)
        renderCurrentTrackState(state.isPlaying)
        // remotePlayerLayout.isVisible = state.remotePlayerRea
    }


    override fun onTrackClicked(track: TrackResponse) {
        viewModel.dispatch(Action.RecommendedTrackClicked(track))
    }

    private fun renderCurrentTrackState(isPlaying: Boolean) {
        val imageRes = if (isPlaying) R.drawable.ic_pause_black else R.drawable.ic_play_arrow
        remotePlayerPlayBtn.setImageResource(imageRes)

    }

    private fun renderTrackFeatures(features: Source<TrackFeaturesResponse>) {
        trackFeaturesLayout.transitionToEnd()
        features.data?.let { feats ->
            score1?.setScore(R.string.track_feature_rythm, "${feats.tempo.toInt()} BPM")
            score2?.setKey(feats.key)
            score3?.setScore(R.string.track_feature_loudness, "${feats.loudness.toInt()} dB")

            score4?.setScore(R.string.track_feature_valence, feats.valence.toPercentageString())
            score5.setScore(R.string.track_feature_danceability, feats.danceability.toPercentageString())
            score6.setScore(R.string.track_feature_energy, feats.energy.toPercentageString())

            score7?.setScore(R.string.track_feature_liveness, feats.liveness.toPercentageString())
            score8?.setScore(R.string.track_feature_instrumental, feats.instrumentalness.toPercentageString())
            score9.setScore(R.string.track_feature_acousticness, feats.acousticness.toPercentageString())

        }
    }

    private fun renderTrackDetails(detail: Source<TrackResponse>) {
        detail.data?.let { data ->
            trackImageIv.load(data.album.images.firstOrNull()?.url)
            trackArtistTv.text = data.artists()
            trackAlbumTv.text = data.album.name
            trackDurationTb.text = data.duration_ms.toMmSs()
            trackPopularityTv.text = data.popularity.toDouble().toPercentageString()
            trackNameTv.text = data.name
        }

    }

    private fun renderTrackRecommended(recommendedTracks: Source<RecommendedTracks>) {
        recommendedTracks.data?.let {
            adapter.submitList(it.tracks)
        }
    }
}
