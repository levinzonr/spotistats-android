package cz.levinzonr.spotistats.presentation.ui.main.onrepeat

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import cz.levinzonr.spotistats.models.Item
import cz.levinzonr.spotistats.models.TrackDataResponse
import cz.levinzonr.spotistats.presentation.R
import cz.levinzonr.spotistats.presentation.extensions.inflate
import kotlinx.android.synthetic.main.item_track.view.*

class TrackListAdapter : ListAdapter<Item, TrackListAdapter.ViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_track))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(track: Item) {
            view.trackArtistTv.text = track.artists.first().name
            view.trackNameTv.text = track.name
            view.trackAlbumIv.load(track.album.images.firstOrNull()?.url)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.uri == newItem.uri
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
}