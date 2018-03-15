package com.jamesrich.cryptometer.api

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.jamesrich.cryptometer.model.Cryptocurrency
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by James on 3/14/2018.
 */
class ApiClient {
    companion object {
        private const val BASE_URL = "https://api.coinmarketcap.com/v1/"
        fun getClient(): Retrofit {
            val okHttpClient = OkHttpClient.Builder().build()
            val moshi = Moshi.Builder().build()
            return Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL).addConverterFactory(MoshiConverterFactory.create(moshi)).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        }
    }
}

interface ApiInterface {
    @GET("ticker/")
    fun getCryptocurrenciesObservable(@Query("start") start: String): Observable<List<Cryptocurrency>>

    @GET("ticker/")
    fun getCryptocurrenciesCall(@Query("start") start: String): Call<List<Cryptocurrency>>

}

class ProjectRepository{
    val retrofit = ApiClient.getClient()
    val apiInterface = retrofit.create(ApiInterface::class.java)
    var data = MutableLiveData<List<Cryptocurrency>>()

    fun getCryptocurrenciesLiveData(): LiveData<List<Cryptocurrency>>{
        apiInterface.getCryptocurrenciesCall("0").enqueue(object : Callback<List<Cryptocurrency>>{
            override fun onFailure(call: Call<List<Cryptocurrency>>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<List<Cryptocurrency>>?, response: Response<List<Cryptocurrency>>?) {
                data.value = response?.body()
            }
        })
        return data
    }
}