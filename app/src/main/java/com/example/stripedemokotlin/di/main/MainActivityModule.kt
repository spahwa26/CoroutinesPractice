package com.example.stripedemokotlin.di.main

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stripedemokotlin.initerfaces.StripeAPI
import com.example.stripedemokotlin.ui.main.MainActivity
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class MainActivityModule {

    @MainScope
    @Provides
    internal fun provideAuthApi(retrofit: Retrofit): StripeAPI {
        return retrofit.create(StripeAPI::class.java)
    }

    @MainScope
    @Provides
    internal fun linearLayoutManager(activity: MainActivity): LinearLayoutManager {
        return LinearLayoutManager(activity)
    }

}