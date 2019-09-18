package com.example.stripedemokotlin.ui.main

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stripedemokotlin.R
import com.example.stripedemokotlin.di.application.ViewModelProviderFactory
import com.example.stripedemokotlin.initerfaces.OnRecyclerClickListener
import com.example.stripedemokotlin.models.CardPojo
import com.example.stripedemokotlin.models.MainPojo
import com.example.stripedemokotlin.ui.create_card.CreateCardActivity
import com.example.stripedemokotlin.utils.ApiType
import com.example.stripedemokotlin.utils.Resource
import com.example.stripedemokotlin.utils.convertToPojo
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_create_card.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progressBar
import okhttp3.ResponseBody
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), OnRecyclerClickListener {


    internal var selectedPosition = -1
    private var cardList = ArrayList<CardPojo>()
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var adapter: CardAdapter

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        viewModel = ViewModelProviders.of(this, providerFactory).get(MainViewModel::class.java)

        btnAddCard.setOnClickListener {
            startActivityForResult(
                Intent(this@MainActivity, CreateCardActivity::class.java),
                101
            )
        }
        subscribeObserver()
        callObserverForCardsList()
    }

    private fun callObserverForCardsList() {
        val map = HashMap<String, String>()
        map["object"] = "card"
        viewModel.getDataFromAPI(map, null, "", ApiType.GETCARDS)
    }

    private fun init() {
        rvCards.layoutManager = layoutManager
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 101)
                adCardToStripe(data!!)
            else
                updateCardToStripe(data!!)
        }
    }

    private fun updateCardToStripe(data: Intent) {
        val map = HashMap<String, Any>()
        map["name"] = data.getStringExtra("name")
        map["address_line1"] = data.getStringExtra("address")
        viewModel.getDataFromAPI(emptyMap(), map, cardList[selectedPosition].id, ApiType.UPDATE)
    }

    private fun adCardToStripe(data: Intent) {

        val map = HashMap<String, Any>()
        map["source"] = data.getStringExtra("token")
        viewModel.getDataFromAPI(emptyMap(), map, "", ApiType.ADD)
    }

    private fun deleteCard() {
        viewModel.getDataFromAPI(
            emptyMap(),
            emptyMap(),
            cardList[selectedPosition].id,
            ApiType.DELETE
        )
    }

    private fun subscribeObserver() {
        viewModel.observeCards().observe(this,
            Observer {
                it.getContentIfNotHandled()?.let { resource ->
                    if (resource != null) {
                        when (resource.status) {
                            Resource.Status.SUCCESS -> {
                                progressBar.visibility = View.GONE
                                if (resource.data != null) {
                                    if (resource.type === ApiType.GETCARDS)
                                        setCardList(resource.data)
                                    else
                                        callObserverForCardsList()
                                }
                            }

                            Resource.Status.ERROR -> {
                                progressBar.visibility = View.GONE
                                Toast.makeText(
                                    this@MainActivity,
                                    "Something went wrong...Please try later!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    }
                }

            })

        viewModel.isLoading().observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                progressBar.visibility = it
            }
        })


    }

    private fun setCardList(data: ResponseBody) {
        var pojo: MainPojo? = null
        try {
            pojo = data.convertToPojo(MainPojo::class)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        cardList = pojo?.data ?: ArrayList()
        adapter.setData(cardList, this@MainActivity)
        rvCards.setAdapter(adapter)
    }


    override fun onItemClicked(pos: Int) {
        selectedPosition = pos
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose an option")
        val animals = arrayOf("Update card", "Delete card")
        builder.setItems(animals) { dialog, which ->
            when (which) {
                0 -> {
                    startActivityForResult(
                        Intent(this@MainActivity, CreateCardActivity::class.java).putExtra(
                            "card_data",
                            cardList[pos]
                        ), 102
                    )
                }
                1 -> {
                    deleteCard()
                }
            }
        }

        val dialog = builder.create()
        dialog.show()
    }


    fun progressClick(view: View) {}
}
