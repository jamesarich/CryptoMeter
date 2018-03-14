package com.jamesrich.cryptometer.api

import com.jamesrich.cryptometer.model.Cryptocurrency
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import okhttp3.OkHttpClient
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
    fun getCryptocurrencies(@Query("start") start: String): Observable<List<Cryptocurrency>>
}