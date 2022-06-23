package com.hungngo.themusicapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hungngo.themusicapp.data.model.Track
import com.hungngo.themusicapp.databinding.ItemTrackBinding
import com.hungngo.themusicapp.utils.onClick.OnClickTrackListener

class HomeTrackAdapter : ListAdapter<Track, HomeTrackAdapter.ViewHolder>(Track.diffCallback) {

    private var onClickTrackListener: OnClickTrackListener? = null

    fun setOnClickTrackListener(onClickTrackListener: OnClickTrackListener) {
        this.onClickTrackListener = onClickTrackListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTrackBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClickTrackListener)
    }

    class ViewHolder(private val binding: ItemTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(track: Track, listener: OnClickTrackListener?) {
            binding.apply {
                textTrackName.text = track.name
                root.setOnClickListener {
                    val idAlbum = ""
                    val idTrack: String = track.id ?: ""
                    listener?.onItemClick(idTrack, idAlbum)
                }
            }
        }
    }
}
