package com.example.stripedemokotlin.di.create_card

import androidx.lifecycle.ViewModel
import com.example.stripedemokotlin.di.application.ViewModelKey
import com.example.stripedemokotlin.ui.create_card.CreateCardViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CreateCardViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(CreateCardViewModel::class)
    abstract fun bindAuthViewModel(viewModel: CreateCardViewModel): ViewModel
}