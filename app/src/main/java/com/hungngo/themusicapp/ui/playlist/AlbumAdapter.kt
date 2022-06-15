package com.hungngo.themusicapp.ui.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hungngo.themusicapp.data.model.Track
import com.hungngo.themusicapp.databinding.ItemTrackInAlbumBinding

class AlbumAdapter : ListAdapter<Track, AlbumAdapter.ViewHolder>(Track.diffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTrackInAlbumBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemTrackInAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(track: Track) {
            binding.apply {
                textAlbumTrack.text = track.name
                textArtistTrack.text = track.artists?.first()?.name
            }
        }
    }
}
