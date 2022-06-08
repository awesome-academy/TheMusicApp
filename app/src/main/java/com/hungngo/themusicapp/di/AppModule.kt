package com.hungngo.themusicapp.di

import com.hungngo.themusicapp.utils.dispatchers.BaseDispatcherProvider
import com.hungngo.themusicapp.utils.dispatchers.DispatcherProvider
import org.koin.dsl.module

val AppModule = module {
    single { provideBaseDispatcherProvider() }
}

fun provideBaseDispatcherProvider(): BaseDispatcherProvider {
    return DispatcherProvider()
}
