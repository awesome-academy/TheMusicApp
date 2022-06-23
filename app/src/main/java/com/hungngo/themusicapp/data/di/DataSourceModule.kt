package com.hungngo.themusicapp.data.di

import com.hungngo.themusicapp.data.source.*
import com.hungngo.themusicapp.data.source.local.TrackLocalImpl
import com.hungngo.themusicapp.data.source.remote.*
import org.koin.dsl.module

val DataSourceModule = module {

    single<PlayListDataSource.Remote> { PlayListRemoteImpl(get()) }

    single<TrackDataSource.Remote> { TrackRemoteImpl(get()) }

    single<ItemSearchDataSource.Remote> { ItemSearchRemoteImpl(get()) }

    single<AlbumDataSource.Remote> { AlbumRemoteImpl(get()) }

    single<LyricDataSource.Remote> { LyricRemoteImpl(get()) }

    single<TrackDataSource.Local> {TrackLocalImpl(get())}
}
