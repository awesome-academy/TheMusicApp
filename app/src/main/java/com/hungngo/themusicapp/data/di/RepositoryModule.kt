package com.hungngo.themusicapp.data.di

import com.hungngo.themusicapp.data.*
import com.hungngo.themusicapp.data.repository.*
import com.hungngo.themusicapp.data.source.*
import com.hungngo.themusicapp.data.source.local.TrackLocalImpl
import com.hungngo.themusicapp.data.source.remote.*
import org.koin.dsl.module

val RepositoryModule = module {
    single { providePlayListRepository(PlayListRemoteImpl(get())) }

    single { provideTrackRepository(TrackRemoteImpl(get()), TrackLocalImpl(get())) }

    single { provideArtistRepository(ItemSearchRemoteImpl(get())) }

    single { provideAlbumRepository(AlbumRemoteImpl(get())) }

    single { provideLyricRepository(LyricRemoteImpl(get())) }
}

fun providePlayListRepository(remote: PlayListDataSource.Remote): PlayListRepository {
    return PlayListRepositoryImpl(remote)
}

fun provideTrackRepository(remote: TrackDataSource.Remote, local: TrackDataSource.Local): TrackRepository =
    TrackRepositoryImpl(remote, local)

fun provideArtistRepository(remote: ItemSearchDataSource.Remote): ItemSearchRepository =
    ItemSearchRepositoryImpl(remote)

fun provideAlbumRepository(remote: AlbumDataSource.Remote): AlbumRepository =
    AlbumRepositoryImpl(remote)

fun provideLyricRepository(remote: LyricDataSource.Remote): LyricRepository =
    LyricRepositoryImpl(remote)
