package com.example.stripedemokotlin.network

import android.util.Log
import com.example.stripedemokotlin.utils.*
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class SimpleAPICall {


//    fun getData(
//        type: ApiType,
//        dataCall: Call<ResponseBody>,
//        showDialog: Boolean,
//        hideDialog: Boolean,
//        dataLoadListener: OnDataLoadListener
//    ) {
//
//        if (showDialog)
//            dataLoadListener.progressDialog(true)
//
//        dataCall.enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//
//                if (hideDialog)
//                    dataLoadListener.progressDialog(false)
//
//                if (response.body() != null)
//                    dataLoadListener.onDataLoad(response.body()!!, type)
//                else
//                    dataLoadListener.onFailiure()
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                if (hideDialog)
//                    dataLoadListener.progressDialog(false)
//
//                dataLoadListener.onFailiure()
//                t.printStackTrace()
//            }
//        })
//
//
//    }


    suspend fun <T : Any> getData(type: ApiType,
                                  call: suspend () -> Response<T>) : Event<Resource<T>> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                return Event(Resource.success(response.body(),type))
            } else {
                if (response.code() == 401){
                    throw UnauthorisedUserException("")
                }
                val error = response.errorBody()?.string()
                val message = StringBuilder()
                error?.let {
                    try {
                        message.append(JSONObject(it).getJSONObject("error").getJSONArray("error_message")[0])
                    } catch (e: JSONException) {
                        message.append("Something went wrong. Please try again later!")
                    }
                }
                throw IOException(message.toString())

                return Event(Resource.error(message.toString(), response.body(),type))
            }
        } catch (e: SocketTimeoutException) {
            throw ApiException("Unable to connect to server right now. Please try again later!")
        } catch (e: ConnectException) {
            throw NoInternetException("Please check your Internet Connection.")
        }catch (e: UnknownHostException){
            Log.e("Exception", e.message)
            throw NoInternetException("Please check your Internet Connection.")
        }
    }
}