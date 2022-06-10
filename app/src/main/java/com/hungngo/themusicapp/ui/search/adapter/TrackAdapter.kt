package com.hungngo.themusicapp.ui.search.adapter

import com.hungngo.themusicapp.R
import com.hungngo.themusicapp.base.BaseListAdapter
import com.hungngo.themusicapp.data.model.ItemSearch

class TrackAdapter : BaseListAdapter<ItemSearch>(ItemSearch.diffCallback){

    override fun getItemViewType(position: Int): Int = R.layout.item_search_track
}
