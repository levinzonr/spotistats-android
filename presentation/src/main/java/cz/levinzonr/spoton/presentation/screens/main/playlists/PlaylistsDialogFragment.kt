package cz.levinzonr.spoton.presentation.screens.main.playlists

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import cz.levinzonr.spoton.models.PlaylistResponse
import cz.levinzonr.spoton.presentation.R
import cz.levinzonr.spoton.presentation.extensions.observeNonNull
import kotlinx.android.synthetic.main.fragment_playlists_dialog.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class PlaylistsDialogFragment : BottomSheetDialogFragment(), PlaylistsAdapter.PlaylistItemListener {

    private val args: PlaylistsDialogFragmentArgs by navArgs()
    private val viewModel: PlaylistsViewModel by viewModel { parametersOf(args.trackIds) }
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
        progressBar.isVisible = state.isLoading
        state.duplicateDialog?.consume()?.let(this::renderConfirmationDialog)
        state.successEvent?.consume()?.let {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

    private fun renderConfirmationDialog(playlistResponse: PlaylistResponse) = AlertDialog.Builder(context)
            .setTitle("Duplicates")
            .setMessage("Some of the tracks are already present in this playlist, add them anyway?")
            .setPositiveButton("Skip duplicates") { dialogInterface, i ->
                viewModel.dispatch(Action.PlaylistClicked(playlistResponse, true))
                dialogInterface.dismiss()
            }
            .setNegativeButton("Add anyway") { dialogInterface, i ->
                viewModel.dispatch(Action.PlaylistClicked(playlistResponse, false))
                dialogInterface.dismiss()

            }.show()


    override fun onPlaylistClicked(playlistResponse: PlaylistResponse) {
        viewModel.dispatch(Action.PlaylistClicked(playlistResponse))
    }
}