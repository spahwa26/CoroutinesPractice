package com.example.stripedemokotlin.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stripedemokotlin.R
import com.example.stripedemokotlin.initerfaces.OnRecyclerClickListener
import com.example.stripedemokotlin.models.CardPojo
import com.vinaygaba.creditcardview.CreditCardView
import kotlinx.android.synthetic.main.layout_card.view.*
import javax.inject.Inject

public class CardAdapter @Inject
constructor() : RecyclerView.Adapter<CardAdapter.MyViewHolder>() {

    private var cardList: List<CardPojo>? = null
    lateinit var listener: OnRecyclerClickListener

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var card: CreditCardView = view.card

    }

    fun setData(cardList: List<CardPojo>, listener: OnRecyclerClickListener) {
        this.cardList = cardList
        this.listener = listener
    }

    fun updateData(cardList: List<CardPojo>) {
        this.cardList = cardList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_card, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cardPojo = cardList!![position]
        if (cardPojo.name != null)
            holder.card.setCardName(cardPojo.name)
        holder.card.setExpiryDate(""+cardPojo.exp_month + "/" + cardPojo.exp_year)
        holder.card.setCardNumber(cardPojo.last4)

        holder.itemView.setOnClickListener { listener.onItemClicked(position) }
    }

    override fun getItemCount(): Int {
        return cardList!!.size
    }
}