package com.hungngo.themusicapp.data.di

import com.hungngo.themusicapp.data.repository.PlayListRepositoryImpl
import com.hungngo.themusicapp.data.source.PlayListDataSource
import com.hungngo.themusicapp.data.PlayListRepository
import com.hungngo.themusicapp.data.TrackRepository
import com.hungngo.themusicapp.data.repository.TrackRepositoryImpl
import com.hungngo.themusicapp.data.source.TrackDataSource
import com.hungngo.themusicapp.data.source.remote.PlayListRemoteImpl
import com.hungngo.themusicapp.data.source.remote.TrackRemoteImpl
import org.koin.dsl.module

val RepositoryModule = module {
    single { providePlayListRepository(PlayListRemoteImpl(get())) }

    single { provideTrackRepository(TrackRemoteImpl(get())) }
}

fun providePlayListRepository(remote: PlayListDataSource.Remote): PlayListRepository {
    return PlayListRepositoryImpl(remote)
}

fun provideTrackRepository(remote: TrackDataSource.Remote): TrackRepository =
    TrackRepositoryImpl(remote)
