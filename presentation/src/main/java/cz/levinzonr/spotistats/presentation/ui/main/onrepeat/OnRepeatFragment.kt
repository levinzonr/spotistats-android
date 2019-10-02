package cz.levinzonr.spotistats.presentation.ui.main.onrepeat


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.spotistats.presentation.R
import cz.levinzonr.spotistats.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_on_repeat.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class OnRepeatFragment : BaseFragment<State>() {


    override val viewModel: OnRepeatViewModel by viewModel()

    private lateinit var adapter: TrackListAdapter

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
        adapter.submitList(state.items)
    }

    private fun setupRecyclerView() {
        adapter = TrackListAdapter()
        tracksRv.adapter  = adapter
    }

}
