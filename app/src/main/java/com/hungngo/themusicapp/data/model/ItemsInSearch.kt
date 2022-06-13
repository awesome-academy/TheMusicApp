package com.hungngo.themusicapp.data.model

import com.google.gson.annotations.SerializedName

data class ItemsInSearch(
    @SerializedName("items")
    val itemSearches: List<ItemSearch>? = null,
    @SerializedName("totalCount")
    val total: Int? = 1,
    @SerializedName("pagingInfo")
    val pagingInfo: PagingInfo
)
