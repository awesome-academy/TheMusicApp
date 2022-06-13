package com.hungngo.themusicapp.ui.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hungngo.themusicapp.R
import com.hungngo.themusicapp.base.BaseListAdapter
import com.hungngo.themusicapp.data.model.ItemSearch

class ArtistListAdapter : BaseListAdapter<ItemSearch>(DiffCallback()){

    class DiffCallback : DiffUtil.ItemCallback<ItemSearch>() {
        override fun areItemsTheSame(oldItemSearch: ItemSearch, newItemSearch: ItemSearch): Boolean {
            return oldItemSearch.data.id == newItemSearch.data.id
        }

        override fun areContentsTheSame(oldItemSearch: ItemSearch, newItemSearch: ItemSearch): Boolean {
            return oldItemSearch == newItemSearch
        }
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_search_artist
}
