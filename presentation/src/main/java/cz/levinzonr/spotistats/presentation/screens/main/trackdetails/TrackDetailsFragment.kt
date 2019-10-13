package cz.levinzonr.spotistats.presentation.screens.main.trackdetails


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import coil.api.load
import cz.levinzonr.spotistats.models.RecommendedTracks
import cz.levinzonr.spotistats.models.TrackFeaturesResponse
import cz.levinzonr.spotistats.models.TrackResponse

import cz.levinzonr.spotistats.presentation.R
import cz.levinzonr.spotistats.presentation.base.BaseFragment
import cz.levinzonr.spotistats.presentation.base.BaseViewModel
import cz.levinzonr.spotistats.presentation.util.Source
import kotlinx.android.synthetic.main.fragment_track_details.*
import kotlinx.android.synthetic.main.include_track_details.*
import kotlinx.android.synthetic.main.include_track_features.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class TrackDetailsFragment : BaseFragment<State>() {

    private val args: TrackDetailsFragmentArgs by navArgs()
    override val viewModel: TrackDetailsViewModel by viewModel { parametersOf(args.trackId) }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_track_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        remotePlayerPlayBtn.setOnClickListener { viewModel.dispatch(Action.PlayTrackClicked(args.trackId)) }
        remotePlayerQueueBtn.setOnClickListener { viewModel.dispatch(Action.QueueTrackClicked(args.trackId)) }
    }


    override fun renderState(state: State) {
        renderTrackDetails(state.trackSource)
        renderTrackFeatures(state.featuresSource)
        renderTrackRecommended(state.recommendedSource)
        remotePlayerLayout.isVisible = state.remotePlayerReady
    }


    private fun renderTrackFeatures(features: Source<TrackFeaturesResponse>) {
        features.data?.let { feats ->
            scoreEnergy.setScore("Energy", feats.energy.toString())
            scoreLoudness.setScore("Loudness", feats.loudness.toString())
            scoreTempo.setScore("Rhythm", feats.tempo.toString())
            scoreAcoustic.setScore("Acoustic", feats.acousticness.toString())
            scoreDancebility.setScore("Dancebility", feats.danceability.toString())
            scoreValence.setScore("Valence", feats.valence.toString())
            scoreInstrumentalness.setScore("Instrumentalness", feats.instrumentalness.toString())
            scoreSpeachness.setScore("Speachness", feats.speechiness.toString())
            scoreLiveness.setScore("Liveness", feats.liveness.toString())
        }
    }

    private fun renderTrackDetails(detail: Source<TrackResponse>) {
        Timber.d("Details state: $detail")
        trackImageIv.load(detail.data?.album?.images?.firstOrNull()?.url)
        trackArtistTv.text = detail.data?.artists?.firstOrNull()?.name
        trackDurationTb.text = detail.data?.duration_ms.toString()
        trackPopularityTv.text = detail.data?.popularity?.toString()
        trackNameTv.text = detail.data?.name
    }

    private fun renderTrackRecommended(recommendedTracks: Source<RecommendedTracks>) {
        Timber.d("Recommended state: $recommendedTracks")
    }
}
