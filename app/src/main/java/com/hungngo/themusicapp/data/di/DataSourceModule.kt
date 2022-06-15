package com.hungngo.themusicapp.data.di

import com.hungngo.themusicapp.data.source.AlbumDataSource
import com.hungngo.themusicapp.data.source.ItemSearchDataSource
import com.hungngo.themusicapp.data.source.PlayListDataSource
import com.hungngo.themusicapp.data.source.TrackDataSource
import com.hungngo.themusicapp.data.source.remote.AlbumRemoteImpl
import com.hungngo.themusicapp.data.source.remote.ItemSearchRemoteImpl
import com.hungngo.themusicapp.data.source.remote.PlayListRemoteImpl
import com.hungngo.themusicapp.data.source.remote.TrackRemoteImpl
import org.koin.dsl.module

val DataSourceModule = module {

    single<PlayListDataSource.Remote> { PlayListRemoteImpl(get()) }

    single<TrackDataSource.Remote> { TrackRemoteImpl(get()) }

    single<ItemSearchDataSource.Remote> { ItemSearchRemoteImpl(get()) }

    single<AlbumDataSource.Remote> { AlbumRemoteImpl(get()) }
}
