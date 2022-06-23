package com.hungngo.themusicapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hungngo.themusicapp.data.model.Track
import com.hungngo.themusicapp.databinding.ItemFavoriteBinding
import com.hungngo.themusicapp.utils.onClick.OnItemRecyclerViewClickListener

class FavoriteTrackAdapter :
    ListAdapter<Track, FavoriteTrackAdapter.ViewHolder>(Track.diffCallback) {

    private var listener: OnItemRecyclerViewClickListener<Int>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFavoriteBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    fun setListener(listener: OnItemRecyclerViewClickListener<Int>) {
        this.listener = listener
    }

    class ViewHolder(
        private val binding: ItemFavoriteBinding,
        private val listener: OnItemRecyclerViewClickListener<Int>?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(track: Track, position: Int) {
            binding.apply {
                textFavoriteTrackName.text = track.name
                textFavoriteTrackSinger.text = track.artistName
                root.setOnClickListener {
                    listener?.onItemClick(position)
                }
                root.setOnLongClickListener {
                    listener?.onItemLongClick(position)
                    return@setOnLongClickListener true
                }
            }
        }
    }
}
