package com.hungngo.themusicapp.data

import com.hungngo.themusicapp.data.model.ItemsSearchResponse
import com.hungngo.themusicapp.utils.DataResult

interface ItemSearchRepository {

    suspend fun searchArtist(query: String, type: String) : DataResult<ItemsSearchResponse>
}
