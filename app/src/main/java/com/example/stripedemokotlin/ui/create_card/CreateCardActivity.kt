package com.example.stripedemokotlin.ui.create_card

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.stripedemokotlin.R
import com.example.stripedemokotlin.di.application.ViewModelProviderFactory
import com.example.stripedemokotlin.models.CardPojo
import com.example.stripedemokotlin.utils.toast
import com.stripe.android.Stripe
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_create_card.*
import javax.inject.Inject

class CreateCardActivity : DaggerAppCompatActivity() {


    lateinit var viewModel: CreateCardViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)

        viewModel =
            ViewModelProviders.of(this, providerFactory).get(CreateCardViewModel::class.java)

        if (intent.hasExtra("card_data")) {
            val pojo = intent.getSerializableExtra("card_data") as CardPojo
            card_input_widget.setCardNumber(pojo.last4)
            card_input_widget.setExpiryDate(pojo.exp_month, pojo.exp_year)
            card_input_widget.isEnabled = false
            etName.setText(pojo.name)
            etAddress.setText(pojo.address_line1)
            btnSave.setOnClickListener {
                val intent = Intent()
                intent.putExtra("name", etName.text.toString())
                intent.putExtra("address", etAddress.text.toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        } else
            btnSave.setOnClickListener {
                var card = card_input_widget.getCard()
                if (card != null && card.validateCard()) {
                    card = card.toBuilder().name(etName.text.toString()).build()
                    card = card.toBuilder().addressLine1(etAddress.text.toString()).build()
                    viewModel.createToken(card)
                } else
                    this?.toast("Please fill the details.")
            }

        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.tokenSuccessEvent().observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                setResult(it.first, it.second)
                finish()
            }
        })

        viewModel.tokenErrorEvent().observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                this?.toast(it)
            }
        })

        viewModel.isLoading().observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                progressBar.visibility=it
            }
        })

    }

    fun progressClick(view: View) {}
}
