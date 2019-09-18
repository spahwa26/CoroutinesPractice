package com.example.stripedemokotlin.di.create_card

import com.example.stripedemokotlin.ui.create_card.CreateCardActivity
import com.stripe.android.Stripe
import dagger.Module
import dagger.Provides

@Module
class CreateCardModule {

    @CreateCardScope
    @Provides
    internal fun provideStripe(activity: CreateCardActivity): Stripe {
        return Stripe(activity, "pk_test_efEIxKtTK7oAVCf7QOGN2QId008grBMHsc")
    }
}