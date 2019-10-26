package cz.levinzonr.spotistats.presentation.screens.main.playlists

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import cz.levinzonr.spotistats.models.PlaylistResponse
import cz.levinzonr.spotistats.presentation.R
import cz.levinzonr.spotistats.presentation.extensions.inflate
import kotlinx.android.synthetic.main.item_playlist.view.*

class PlaylistsAdapter : ListAdapter<PlaylistResponse, PlaylistsAdapter.ViewHolder>(DiffCallback()) {

    var listener: PlaylistItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_playlist))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(playlistResponse: PlaylistResponse) {
            view.playlistNameTv.text = playlistResponse.name
            view.playlistImageIv.load(playlistResponse.images.firstOrNull()?.url)
            view.setOnClickListener { listener?.onPlaylistClicked(playlistResponse) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PlaylistResponse>() {
        override fun areItemsTheSame(oldItem: PlaylistResponse, newItem: PlaylistResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PlaylistResponse, newItem: PlaylistResponse): Boolean {
            return oldItem == newItem
        }
    }

    interface PlaylistItemListener {
        fun onPlaylistClicked(playlistResponse: PlaylistResponse)
    }

}