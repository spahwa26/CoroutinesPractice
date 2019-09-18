package com.example.stripedemokotlin.di.main

import androidx.lifecycle.ViewModel
import com.example.stripedemokotlin.di.application.ViewModelKey
import com.example.stripedemokotlin.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindAuthViewModel(mainViewModel: MainViewModel) : ViewModel

}