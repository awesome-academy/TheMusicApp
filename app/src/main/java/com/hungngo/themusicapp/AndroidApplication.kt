package com.hungngo.themusicapp

import android.app.Application
import com.hungngo.themusicapp.data.di.DataSourceModule
import com.hungngo.themusicapp.data.di.NetworkModule
import com.hungngo.themusicapp.data.di.RepositoryModule
import com.hungngo.themusicapp.data.di.roomModule
import com.hungngo.themusicapp.di.AppModule
import com.hungngo.themusicapp.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin

class AndroidApplication : Application() {
    private val rootModule =
        listOf(AppModule, NetworkModule, roomModule, DataSourceModule, RepositoryModule, ViewModelModule)

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AndroidApplication)
            androidFileProperties()
            modules(rootModule)
        }
    }
}
