package com.example.stripedemokotlin.di.application


import com.example.stripedemokotlin.di.create_card.CreateCardModule
import com.example.stripedemokotlin.di.create_card.CreateCardScope
import com.example.stripedemokotlin.di.create_card.CreateCardViewModelsModule
import com.example.stripedemokotlin.di.main.MainActivityModule
import com.example.stripedemokotlin.di.main.MainScope
import com.example.stripedemokotlin.di.main.MainViewModelsModule
import com.example.stripedemokotlin.ui.create_card.CreateCardActivity
import com.example.stripedemokotlin.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {


    @MainScope
    @ContributesAndroidInjector(modules = [MainViewModelsModule::class, MainActivityModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

    @CreateCardScope
    @ContributesAndroidInjector(modules = [CreateCardViewModelsModule::class, CreateCardModule::class])
    internal abstract fun contributeCreateCardActivity(): CreateCardActivity


}