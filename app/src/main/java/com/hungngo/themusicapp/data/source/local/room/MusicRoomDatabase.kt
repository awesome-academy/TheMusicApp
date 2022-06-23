package com.hungngo.themusicapp.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hungngo.themusicapp.data.model.Track

@Database(entities = [Track::class], version = 1)
abstract class MusicRoomDatabase : RoomDatabase() {
    abstract fun songDAO(): SongDAO
}
