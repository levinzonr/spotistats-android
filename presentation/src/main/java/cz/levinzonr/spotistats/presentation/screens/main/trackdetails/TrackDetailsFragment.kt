package cz.levinzonr.spotistats.presentation.screens.main.trackdetails


import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.api.load
import cz.levinzonr.spotistats.models.RecommendedTracks
import cz.levinzonr.spotistats.models.TrackFeaturesResponse
import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.models.artists
import cz.levinzonr.spotistats.presentation.R
import cz.levinzonr.spotistats.presentation.base.BaseFragment
import cz.levinzonr.spotistats.presentation.extensions.toMmSs
import cz.levinzonr.spotistats.presentation.extensions.toPercentageString
import cz.levinzonr.spotistats.presentation.screens.main.onrepeat.TrackListAdapter
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

        val hitRect = Rect()
        trackFeaturesLayout.getHitRect(hitRect)

       root.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
           if (v.getGlobalVisibleRect(hitRect)) {
               trackFeaturesLayout.transitionToEnd()
           } else {
               trackFeaturesLayout.transitionToStart()
           }
       }

    }


    override fun renderState(state: State) {
        renderTrackDetails(state.trackSource)
        renderTrackFeatures(state.featuresSource)
        renderTrackRecommended(state.recommendedSource)
        // remotePlayerLayout.isVisible = state.remotePlayerReady

    }


    override fun onTrackClicked(track: TrackResponse) {
        viewModel.dispatch(Action.RecommendedTrackClicked(track))
    }

    private fun renderTrackFeatures(features: Source<TrackFeaturesResponse>) {
        features.data?.let { feats ->
            scoreEnergy?.setScore("Energy", feats.energy.toPercentageString())
            scoreLoudness?.setScore("Loudness", feats.loudness.toPercentageString())
            scoreTempo?.setScore("Rhythm", feats.tempo.toString())
            scoreAcoustic?.setScore("Acoustic", feats.acousticness.toPercentageString())
            scoreDancebility?.setScore("Dancebility", feats.danceability.toPercentageString())
            scoreValence?.setScore("Valence", feats.valence.toPercentageString())
            scoreInstrumentalness?.setScore("Instrumentalness", feats.instrumentalness.toPercentageString())
            scoreSpeachness?.setScore("Speachness", feats.speechiness.toPercentageString())
            scoreLiveness?.setScore("Liveness", feats.liveness.toPercentageString())
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
