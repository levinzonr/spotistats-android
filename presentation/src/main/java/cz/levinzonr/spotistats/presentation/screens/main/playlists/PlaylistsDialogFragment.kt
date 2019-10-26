package cz.levinzonr.spotistats.presentation.screens.main.playlists

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import cz.levinzonr.spotistats.models.PlaylistResponse
import cz.levinzonr.spotistats.presentation.R
import cz.levinzonr.spotistats.presentation.extensions.observeNonNull
import kotlinx.android.synthetic.main.fragment_playlists_dialog.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class PlaylistsDialogFragment : BottomSheetDialogFragment(), PlaylistsAdapter.PlaylistItemListener {

    private val args: PlaylistsDialogFragmentArgs by navArgs()
    private val viewModel: PlaylistsViewModel by viewModel { parametersOf(args.trackId) }
    private lateinit var adapter: PlaylistsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_playlists_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.observableState.observeNonNull(viewLifecycleOwner, this::renderState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PlaylistsAdapter()
        adapter.listener = this
        list.adapter = adapter
    }


    private fun renderState(state: State) {
        adapter.submitList(state.playlists)
        state.duplicateDialog?.consume()?.let(this::renderConfirmationDialog)
        state.successEvent?.consume()?.let {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

    private fun renderConfirmationDialog(playlistResponse: PlaylistResponse) = AlertDialog.Builder(context)
            .setTitle("Duplicate")
            .setMessage("Some of the tracks are already present in the playlist, added them anyway?")
            .setPositiveButton("Yes") { dialogInterface, i -> viewModel.dispatch(Action.PlaylistClicked(playlistResponse)); dialogInterface.dismiss() }
            .setNegativeButton("No") { dialogInterface, i -> dialogInterface.dismiss() }
            .show()

    override fun onPlaylistClicked(playlistResponse: PlaylistResponse) {
        viewModel.dispatch(Action.PlaylistClicked(playlistResponse))
    }
}