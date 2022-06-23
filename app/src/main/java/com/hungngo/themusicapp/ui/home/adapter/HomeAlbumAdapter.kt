package com.hungngo.themusicapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hungngo.themusicapp.data.model.Album
import com.hungngo.themusicapp.databinding.ItemPlaylistBinding
import com.hungngo.themusicapp.utils.extension.loadImageWithUrl
import com.hungngo.themusicapp.utils.onClick.OnClickTrackListener

class HomeAlbumAdapter : ListAdapter<Album, HomeAlbumAdapter.ViewHolder>(Album.diffCallback) {

    private var onClickTrackListener: OnClickTrackListener? = null

    fun setOnClickTrackListener(onClickTrackListener: OnClickTrackListener) {
        this.onClickTrackListener = onClickTrackListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaylistBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClickTrackListener)
    }

    class ViewHolder(private val binding: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album, listener: OnClickTrackListener?) {
            binding.apply {
                textPlaylistName.text = album.name
                album.images?.first()?.url?.let { imagePlaylist.loadImageWithUrl(it) }
                root.setOnClickListener {
                    val idAlbum = album.id ?: ""
                    val idTrack: String = album.tracks?.items?.first()?.id ?: ""
                    listener?.onItemClick(idTrack, idAlbum)
                }
            }
        }
    }
}
