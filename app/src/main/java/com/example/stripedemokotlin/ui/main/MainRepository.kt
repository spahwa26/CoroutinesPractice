package com.example.stripedemokotlin.ui.main

import android.util.Log
import com.example.stripedemokotlin.initerfaces.StripeAPI
import com.example.stripedemokotlin.network.SimpleAPICall
import com.example.stripedemokotlin.utils.ApiType
import com.example.stripedemokotlin.utils.Resource
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(val stripeAPI: StripeAPI): SimpleAPICall() {

    init {
        Log.e("MainRepository", stripeAPI.toString())
    }

    suspend fun getDataFromAPI(
        getCardsMap: Map<String, String>?,
        addUpdateMap: Map<String, Any>?,
        id: String?,
        type: ApiType
    ) = getData(type) {getCallObj(type,getCardsMap, addUpdateMap, id)}

    suspend fun getCallObj(type: ApiType,
                           getCardsMap: Map<String, String>?,
                           addUpdateMap: Map<String, Any>?,
                           id: String?) : Response<ResponseBody> {
        if (type === ApiType.GETCARDS)
            return stripeAPI.getAllCards(getCardsMap!!)
        else if (type === ApiType.ADD)
            return stripeAPI.addCard(addUpdateMap!!)
        else if (type === ApiType.DELETE)
            return  stripeAPI.deleteCard(id!!)
        else
            return stripeAPI.updateCard(id!!, addUpdateMap!!)
    }

}