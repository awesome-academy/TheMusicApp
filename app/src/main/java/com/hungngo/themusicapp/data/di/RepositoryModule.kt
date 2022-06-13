package com.hungngo.themusicapp.data.di

import com.hungngo.themusicapp.data.AlbumRepository
import com.hungngo.themusicapp.data.ItemSearchRepository
import com.hungngo.themusicapp.data.repository.PlayListRepositoryImpl
import com.hungngo.themusicapp.data.source.PlayListDataSource
import com.hungngo.themusicapp.data.PlayListRepository
import com.hungngo.themusicapp.data.TrackRepository
import com.hungngo.themusicapp.data.repository.AlbumRepositoryImpl
import com.hungngo.themusicapp.data.repository.ItemSearchRepositoryImpl
import com.hungngo.themusicapp.data.repository.TrackRepositoryImpl
import com.hungngo.themusicapp.data.source.AlbumDataSource
import com.hungngo.themusicapp.data.source.ItemSearchDataSource
import com.hungngo.themusicapp.data.source.TrackDataSource
import com.hungngo.themusicapp.data.source.remote.AlbumRemoteImpl
import com.hungngo.themusicapp.data.source.remote.ItemSearchRemoteImpl
import com.hungngo.themusicapp.data.source.remote.PlayListRemoteImpl
import com.hungngo.themusicapp.data.source.remote.TrackRemoteImpl
import org.koin.dsl.module

val RepositoryModule = module {
    single { providePlayListRepository(PlayListRemoteImpl(get())) }

    single { provideTrackRepository(TrackRemoteImpl(get())) }

    single { provideArtistRepository(ItemSearchRemoteImpl(get())) }

    single { provideAlbumRepository(AlbumRemoteImpl(get())) }
}

fun providePlayListRepository(remote: PlayListDataSource.Remote): PlayListRepository {
    return PlayListRepositoryImpl(remote)
}

fun provideTrackRepository(remote: TrackDataSource.Remote): TrackRepository =
    TrackRepositoryImpl(remote)

fun provideArtistRepository(remote: ItemSearchDataSource.Remote): ItemSearchRepository =
    ItemSearchRepositoryImpl(remote)

fun provideAlbumRepository(remote: AlbumDataSource.Remote): AlbumRepository =
    AlbumRepositoryImpl(remote)
