package com.hungngo.themusicapp.di

import com.hungngo.themusicapp.ui.main.MainViewModel
import com.hungngo.themusicapp.ui.home.HomeViewModel
import com.hungngo.themusicapp.ui.playlist.PlaylistViewModel
import com.hungngo.themusicapp.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val ViewModelModule: Module = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { PlaylistViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get(), get()) }
}
