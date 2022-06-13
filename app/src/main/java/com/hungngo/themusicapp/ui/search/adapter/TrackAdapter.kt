package com.hungngo.themusicapp.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hungngo.themusicapp.data.model.ItemSearch
import com.hungngo.themusicapp.databinding.ItemSearchTrackBinding
import com.hungngo.themusicapp.utils.onClick.OnClickTrackListener

class TrackAdapter : ListAdapter<ItemSearch, TrackAdapter.ViewHolder>(ItemSearch.diffCallback) {

    private var onClickTrackListener: OnClickTrackListener? = null

    fun setOnClickTrackListener(onClickTrackListener: OnClickTrackListener) {
        this.onClickTrackListener = onClickTrackListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchTrackBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClickTrackListener)
    }

    class ViewHolder(private val binding: ItemSearchTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemSearch, listener: OnClickTrackListener?) {
            binding.apply {
                setItem(item)
                executePendingBindings()
                root.setOnClickListener {
                    listener?.onItemClick(item.data)
                }
            }
        }
    }
}
