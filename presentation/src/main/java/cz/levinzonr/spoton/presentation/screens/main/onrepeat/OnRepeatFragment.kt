package cz.levinzonr.spoton.presentation.screens.main.onrepeat


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import cz.levinzonr.spoton.models.TrackResponse
import cz.levinzonr.spoton.presentation.R
import cz.levinzonr.spoton.presentation.base.BaseFragment
import cz.levinzonr.spoton.presentation.base.TrackOptionsDialog
import cz.levinzonr.spoton.presentation.extensions.showOptionsDialog
import cz.levinzonr.spoton.presentation.views.AppDialog
import kotlinx.android.synthetic.main.fragment_on_repeat.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

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
            listOf(longProgressBar, shortProgressBar, midProgressBar).forEach { it.isVisible = state.isLoading }
            shortAdapter.submitList(tracks.tracksShort)
            longAdapter.submitList(tracks.tracksLong)
            midAdapter.submitList(tracks.tracksMid)


            tracksShortMoreBtn.setOnClickListener {
                showOptionsDialog<TrackOptionsDialog> {
                   onOptionSelected(it, tracks.tracksShort)
                }
            }

            tracksLongMoreBtn.setOnClickListener {
                showOptionsDialog<TrackOptionsDialog> {
                    onOptionSelected(it, tracks.tracksLong)

                }
            }

            tracksMidMoreBtn.setOnClickListener {
                showOptionsDialog<TrackOptionsDialog> {
                    onOptionSelected(it, tracks.tracksMid)
                }
            }

        }
    }


    private fun onOptionSelected(id: Int, tracks: List<TrackResponse>) {
        when(id) {
            R.id.tracksAddTopPlaylistBtn -> viewModel.dispatch(Action.AddToPlaylistAction(tracks))
            R.id.tracksNewPlaylistBtn -> showCreatePlaylistDialog(tracks)
        }
    }

    private fun showCreatePlaylistDialog(tracks: List<TrackResponse>) {
        val format = SimpleDateFormat("dd MMMM", Locale.getDefault())
        val defaultName = "Created by SpotiFaves on " + format.format(Date())
        AppDialog.Builder(context)
                .setTitle(getString(R.string.playlist_create_title))
                .setMessage(getString(R.string.playlist_create_message))
                .setInputField(getString(R.string.playlist_create_hint), defaultName) {
                    viewModel.dispatch(Action.CreatePlaylistAction(tracks, it))
                }.show()
    }

    private fun setupRecyclerView() {
        shortAdapter = TrackListAdapter(this)
        longAdapter = TrackListAdapter(this)
        midAdapter = TrackListAdapter(this)
        tracksShortRv.adapter = shortAdapter
        tracksLongRv.adapter = longAdapter
        tracksMidRv.adapter = midAdapter
    }

    override fun onTrackClicked(track: TrackResponse) {
        viewModel.dispatch(Action.TrackClicked(track))
    }
}
