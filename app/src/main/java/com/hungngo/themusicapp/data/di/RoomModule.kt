package com.hungngo.themusicapp.data.di

import android.app.Application
import androidx.room.Room
import com.hungngo.themusicapp.data.source.local.room.MusicRoomDatabase
import com.hungngo.themusicapp.data.source.local.room.SongDAO
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideSongDAO(get()) }
}

fun provideDatabase(application: Application): MusicRoomDatabase {
    return Room.databaseBuilder(application, MusicRoomDatabase::class.java, "track_db")
        .fallbackToDestructiveMigration()
        .build()
}

fun provideSongDAO(database: MusicRoomDatabase) : SongDAO{
    return database.songDAO()
}
