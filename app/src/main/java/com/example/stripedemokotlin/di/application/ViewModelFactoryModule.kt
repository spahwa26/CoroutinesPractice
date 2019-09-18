package com.example.stripedemokotlin.di.application

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module


@Module
public abstract class ViewModelFactoryModule {


    @Binds
    abstract fun bindViewModelFactory(impl: ViewModelProviderFactory): ViewModelProvider.Factory

}
