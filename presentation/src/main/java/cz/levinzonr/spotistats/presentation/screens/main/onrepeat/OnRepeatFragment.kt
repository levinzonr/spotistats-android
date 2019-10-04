package cz.levinzonr.spotistats.presentation.screens.main.onrepeat


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import cz.levinzonr.spotistats.models.TrackResponse

import cz.levinzonr.spotistats.presentation.R
import cz.levinzonr.spotistats.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_on_repeat.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class OnRepeatFragment : BaseFragment<State>(), TrackListAdapter.TrackItemListener {


    override val viewModel: OnRepeatViewModel by viewModel()

    private lateinit var shortAdapter: TrackListAdapter
    private lateinit var midAdapter: TrackListAdapter
    private lateinit var longAdapter: TrackListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_repeat, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }


    override fun renderState(state: State) {
        state.tracks?.let { tracks ->
            listOf(longProgressBar, shortProgressBar, midProgressBar).forEach {it.isVisible = state.isLoading}
            shortAdapter.submitList(tracks.tracksShort)
            longAdapter.submitList(tracks.tracksLong)
            midAdapter.submitList(tracks.tracksMid)
        }
    }

    private fun setupRecyclerView() {
        shortAdapter = TrackListAdapter(this)
        longAdapter = TrackListAdapter(this)
        midAdapter = TrackListAdapter(this)
        tracksShortRv.adapter  = shortAdapter
        tracksLongRv.adapter = longAdapter
        tracksMidRv.adapter = midAdapter
    }

    override fun onTrackClicked(track: TrackResponse) {
        viewModel.dispatch(Action.TrackClicked(track))
    }
}
