package com.hungngo.themusicapp.di

import com.hungngo.themusicapp.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val ViewModelModule: Module = module {
    viewModel { HomeViewModel(get()) }
}
