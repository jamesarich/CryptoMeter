package com.jamesrich.cryptometer.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jamesrich.cryptometer.api.ProjectRepository
import com.jamesrich.cryptometer.model.Cryptocurrency

/**
 * Created by James on 3/14/2018.
 */
class CryptoTickerViewModel : ViewModel() {
    private var repository = ProjectRepository()
    private var cryptocurrencies:MutableLiveData<List<Cryptocurrency>> = repository.cryptocurrencies


    fun getCryptocurrencies():LiveData<List<Cryptocurrency>>{
        if (cryptocurrencies.value == null){
            repository.fetchCryptocurrencies()
        }
        return cryptocurrencies
    }
}