package com.hungngo.themusicapp.data.source.local.room

import androidx.room.*
import com.hungngo.themusicapp.data.model.Track

@Dao
interface SongDAO {

    @Query("SELECT * FROM track_tbl ORDER BY id ASC")
    fun getAllTrack(): List<Track>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: Track)

    @Delete
    suspend fun delete(track: Track)

    @Query("SELECT EXISTS (SELECT 1 FROM track_tbl WHERE id = :idTrack)")
    suspend fun isFavorite(idTrack: String) : Boolean
}
