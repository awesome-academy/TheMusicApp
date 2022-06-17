package com.hungngo.themusicapp.di

import com.hungngo.themusicapp.ui.MainViewModel
import com.hungngo.themusicapp.ui.home.HomeViewModel
import com.hungngo.themusicapp.ui.playlist.PlaylistViewModel
import com.hungngo.themusicapp.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val ViewModelModule: Module = module {
    viewModel { HomeViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { PlaylistViewModel(get()) }
    viewModel { MainViewModel(get(), get())}
}
