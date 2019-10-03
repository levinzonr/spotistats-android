package cz.levinzonr.spotistats.presentation.screens.main.trackdetails


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import cz.levinzonr.spotistats.models.RecommendedTracks
import cz.levinzonr.spotistats.models.TrackFeaturesResponse
import cz.levinzonr.spotistats.models.TrackResponse

import cz.levinzonr.spotistats.presentation.R
import cz.levinzonr.spotistats.presentation.base.BaseFragment
import cz.levinzonr.spotistats.presentation.base.BaseViewModel
import cz.levinzonr.spotistats.presentation.util.Source
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


    override fun renderState(state: State) {
        renderTrackDetails(state.trackSource)
        renderTrackFeatures(state.featuresSource)
        renderTrackRecommended(state.recommendedSource)
    }


    private fun renderTrackFeatures(features: Source<TrackFeaturesResponse>) {
        Timber.d("Features state: $features")
    }

    private fun renderTrackDetails(detail: Source<TrackResponse>) {
        Timber.d("Details state: $detail")

    }

    private fun renderTrackRecommended(recommendedTracks: Source<RecommendedTracks>) {
        Timber.d("Recommended state: $recommendedTracks")
    }
}
