package com.example.stripedemokotlin.ui.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stripedemokotlin.utils.ApiType
import com.example.stripedemokotlin.utils.Event
import com.example.stripedemokotlin.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

class MainViewModel @Inject constructor( val mainRepository: MainRepository) : ViewModel() {

    private val isLoading= MutableLiveData<Event<Int>>()

    private val allCardsData = MutableLiveData<Event<Resource<ResponseBody>>>()

    fun getDataFromAPI (getCardsMap: Map<String, String>?,
                        addUpdateMap: Map<String, Any>?,
                        id: String?,
                        type: ApiType) = CoroutineScope(Dispatchers.IO).launch {
        isLoading.postValue(Event(View.VISIBLE))
        allCardsData.postValue(mainRepository.getDataFromAPI(getCardsMap, addUpdateMap,id, type))
        isLoading.postValue(Event(View.GONE))
    }


    fun observeCards(): LiveData<Event<Resource<ResponseBody>>> {
        return allCardsData
    }

    fun isLoading() : LiveData<Event<Int>> = isLoading

}