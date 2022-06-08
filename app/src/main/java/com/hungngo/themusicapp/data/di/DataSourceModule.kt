package com.hungngo.themusicapp.data.di

import com.hungngo.themusicapp.data.source.PlayListDataSource
import com.hungngo.themusicapp.data.source.remote.PlayListRemoteImpl
import org.koin.dsl.module

val DataSourceModule = module {

    single<PlayListDataSource.Remote> { PlayListRemoteImpl(get()) }
}
