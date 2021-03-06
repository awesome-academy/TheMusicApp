package com.hungngo.themusicapp.data.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("album_type")
    val albumType: String? = null,
    @SerializedName("artists")
    val artists: List<Artist>? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("images")
    val images: List<Image>? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("release_date_precision")
    val releaseDatePrecision: String? = null,
    @SerializedName("total_tracks")
    val totalTracks: Int? = 1,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("uri")
    val uri: String? = null,
    @SerializedName("coverArt")
    val coverArt: CoverArt? = null,
    @SerializedName("label")
    val label: String? = null,
    @SerializedName("tracks")
    val tracks: Tracks? = null
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(oldIem: Album, newItem: Album): Boolean {
                return oldIem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
                return oldItem == newItem
            }
        }
    }
}
