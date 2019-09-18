package com.example.stripedemokotlin.ui.create_card

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stripedemokotlin.utils.Event
import com.stripe.android.Stripe
import com.stripe.android.TokenCallback
import com.stripe.android.model.Card
import com.stripe.android.model.Token
import javax.inject.Inject

class CreateCardViewModel @Inject constructor(val stripe: Stripe): ViewModel() {

    private val isLoading= MutableLiveData<Event<Int>>()
    private val tokenSuccess= MutableLiveData<Event<Pair<Int, Intent>>>()
    private val tokenError= MutableLiveData<Event<String>>()


    fun createToken(card: Card) {
        isLoading.value= Event(View.VISIBLE)
        stripe?.createToken(
            card,
            object : TokenCallback {
                override fun onSuccess(token: Token) {
                    // send token id to your server for charge creation
                    isLoading.value=Event(View.GONE)
                    val intent = Intent()
                    intent.putExtra("number", card.number)
                    intent.putExtra("exp_month", card.expMonth)
                    intent.putExtra("exp_year", card.expYear)
                    intent.putExtra("token", token.id)
                    intent.putExtra("cvc", card.cvc)
                    tokenSuccess.value=Event(Pair(RESULT_OK, intent))
                }

                override fun onError(error: Exception) {
                    isLoading.value=Event(View.GONE)
                    tokenError.value=Event(error.message.toString())
                }
            }
        )
    }


    fun isLoading() : LiveData<Event<Int>> = isLoading
    fun tokenErrorEvent(): LiveData<Event<String>> = tokenError
    fun tokenSuccessEvent(): LiveData<Event<Pair<Int, Intent>>> = tokenSuccess

}