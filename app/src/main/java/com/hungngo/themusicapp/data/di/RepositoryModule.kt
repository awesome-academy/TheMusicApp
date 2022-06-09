package com.hungngo.themusicapp.data.di

import com.hungngo.themusicapp.data.repository.PlayListRepositoryImpl
import com.hungngo.themusicapp.data.source.PlayListDataSource
import com.hungngo.themusicapp.data.PlayListRepository
import com.hungngo.themusicapp.data.source.remote.PlayListRemoteImpl
import org.koin.dsl.module

val RepositoryModule = module {
    single { providePlayListRepository(PlayListRemoteImpl(get())) }
}

fun providePlayListRepository(remote: PlayListDataSource.Remote): PlayListRepository {
    return PlayListRepositoryImpl(remote)
}
