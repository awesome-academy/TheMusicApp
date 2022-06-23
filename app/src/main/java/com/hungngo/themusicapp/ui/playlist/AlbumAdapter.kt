package com.hungngo.themusicapp.ui.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hungngo.themusicapp.R
import com.hungngo.themusicapp.data.model.Track
import com.hungngo.themusicapp.databinding.ItemTrackInAlbumBinding

class AlbumAdapter : ListAdapter<Track, AlbumAdapter.ViewHolder>(Track.diffCallback) {

    private var listener: OnItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTrackInAlbumBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setListener(listener: OnItemClick) {
        this.listener = listener
    }

    class ViewHolder(
        private val binding: ItemTrackInAlbumBinding,
        private val itemClickListener: OnItemClick?
    ) : RecyclerView.ViewHolder(binding.root) {

        private var trackData: Track? = null
        private var listener: OnItemClick? = null

        init {
            listener = itemClickListener
        }

        fun bind(track: Track) {
            binding.apply {
                textAlbumTrack.text = track.name
                textArtistTrack.text = track.artists?.first()?.name
                trackData = track
                root.setOnClickListener {
                    listener?.onClickTrack(trackData)
                }
                buttonFavorite.apply {
                    setImageResource(if (track.isFavorite) R.drawable.ic_favorite_red else R.drawable.ic_favorite)
                    setOnClickListener {
                        listener?.onClickFavorite(trackData, adapterPosition)
                        if (!track.isFavorite) {
                            track.isFavorite = true
                            setImageResource(R.drawable.ic_favorite_red)
                        }
                    }
                }
            }
        }
    }

    interface OnItemClick {
        fun onClickTrack(track: Track?)
        fun onClickFavorite(track: Track?, position: Int)
    }
}
