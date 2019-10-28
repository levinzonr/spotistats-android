package cz.levinzonr.spoton.presentation.screens.main.onrepeat

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import cz.levinzonr.spoton.models.TrackResponse
import cz.levinzonr.spoton.presentation.R
import cz.levinzonr.spoton.presentation.extensions.inflate
import kotlinx.android.synthetic.main.item_track.view.*

class TrackListAdapter(private val listener: TrackItemListener) : ListAdapter<TrackResponse, TrackListAdapter.ViewHolder>(DiffCallback()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_track))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(track: TrackResponse) {
            view.trackArtistTv.text = track.artists.first().name
            view.trackNameTv.text = track.name
            view.trackAlbumIv.load(track.album.images.firstOrNull()?.url)
            view.setOnClickListener { listener.onTrackClicked(track) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TrackResponse>(){
        override fun areItemsTheSame(oldItem: TrackResponse, newItem: TrackResponse): Boolean {
            return oldItem.uri == newItem.uri
        }

        override fun areContentsTheSame(oldItem: TrackResponse, newItem: TrackResponse): Boolean {
            return oldItem == newItem
        }
    }


    interface TrackItemListener {
        fun onTrackClicked(track: TrackResponse)
    }

}