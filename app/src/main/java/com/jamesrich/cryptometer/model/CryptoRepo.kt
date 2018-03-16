package com.jamesrich.cryptometer.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.jamesrich.cryptometer.api.ApiClient
import com.jamesrich.cryptometer.api.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by James on 3/16/2018.
 */
class CryptoRepo:Repo {
    val retrofit = ApiClient.getClient()
    val apiInterface = retrofit.create(ApiInterface::class.java)
    var cryptocurrencies = MutableLiveData<List<Cryptocurrency>>()

    override fun fetchCryptocurrencies(): LiveData<List<Cryptocurrency>> {
        apiInterface.getCryptocurrenciesCall("0").enqueue(object : Callback<List<Cryptocurrency>> {
            override fun onFailure(call: Call<List<Cryptocurrency>>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<List<Cryptocurrency>>?, response: Response<List<Cryptocurrency>>?) {
                cryptocurrencies.value = response?.body()
            }
        })
        return cryptocurrencies
    }
}

interface Repo{
    fun fetchCryptocurrencies(): LiveData<List<Cryptocurrency>>
}