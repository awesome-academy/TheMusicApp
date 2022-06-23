package com.hungngo.themusicapp.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "track_tbl")
data class Track(
    @SerializedName("album")
    @Ignore
    var album: Album? = null,
    @SerializedName("artists")
    @Ignore
    var artists: ArrayList<Artist>? = null,
    @SerializedName("disc_number")
    @Ignore
    var discNumber: Int = 0,
    @SerializedName("duration_ms")
    var durationMs: Int = 0,
    @SerializedName("explicit")
    @Ignore
    var explicit: Boolean = false,
    @SerializedName("id")
    @PrimaryKey
    var id: String = "",
    @SerializedName("is_local")
    @Ignore
    var isLocal: Boolean = false,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("popularity")
    @Ignore
    var popularity: Int = 0,
    @SerializedName("preview_url")
    var previewUrl: String? = null,
    @SerializedName("track_number")
    @Ignore
    var trackNumber: Int = 0,
    @SerializedName("type")
    @Ignore
    var type: String? = null,
    var artistName: String? = null
) : Serializable {
    @Ignore
    var isFavorite = false

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Track>() {
            override fun areItemsTheSame(oldItemSearch: Track, newItemSearch: Track): Boolean {
                return oldItemSearch.id == newItemSearch.id
            }

            override fun areContentsTheSame(oldItemSearch: Track, newItemSearch: Track): Boolean {
                return oldItemSearch == newItemSearch
            }
        }
    }
}
