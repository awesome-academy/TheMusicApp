package com.hungngo.themusicapp.data.model

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.google.gson.annotations.SerializedName

data class ItemSearch(
    @SerializedName("data")
    val `data`: Data
) {

    companion object {
        val diffCallback = object : ItemCallback<ItemSearch>() {
            override fun areItemsTheSame(oldItem: ItemSearch, newItem: ItemSearch): Boolean {
                return oldItem.data.id == newItem.data.id
            }

            override fun areContentsTheSame(oldItem: ItemSearch, newItem: ItemSearch): Boolean {
                return oldItem == newItem
            }
        }
    }
}
