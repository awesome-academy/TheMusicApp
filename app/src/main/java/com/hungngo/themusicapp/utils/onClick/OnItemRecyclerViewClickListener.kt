package com.hungngo.themusicapp.utils.onClick

interface OnItemRecyclerViewClickListener<T> {
    fun onItemClick(item: T?)
    fun onItemLongClick(item: T?)
}
