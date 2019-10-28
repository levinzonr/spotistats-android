package cz.levinzonr.spoton.presentation.screens.main.profile

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import cz.levinzonr.spoton.models.PlaylistResponse
import cz.levinzonr.spoton.presentation.R
import cz.levinzonr.spoton.presentation.extensions.inflate
import cz.levinzonr.spoton.presentation.screens.main.playlists.PlaylistsAdapter
import kotlinx.android.synthetic.main.item_playlist_recent.view.*

class RecentPlaylistsAdapter : ListAdapter<PlaylistResponse, RecentPlaylistsAdapter.ViewHolder>(PlaylistsAdapter.DiffCallback())  {

    var listener: RecentPlaylistItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_playlist_recent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(playlist: PlaylistResponse) {
            view.playlistCoverIv.load(playlist.images.firstOrNull()?.url)
            view.playlistNameTv.text = playlist.name

            view.playlistPlayOrderedBtn.setOnClickListener {
                listener?.onPlayOrderedClicked(playlist)
            }
            view.playlistPlayShuffledBtn.setOnClickListener {
                listener?.onPlayShuffledClicked(playlist)
            }
        }
    }

    interface RecentPlaylistItemListener {
        fun onPlayOrderedClicked(playlist: PlaylistResponse)
        fun onPlayShuffledClicked(playlist: PlaylistResponse)
    }

}