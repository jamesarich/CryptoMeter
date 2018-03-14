package com.jamesrich.cryptometer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jamesrich.cryptometer.api.ApiClient
import com.jamesrich.cryptometer.api.ApiInterface
import com.jamesrich.cryptometer.model.Cryptocurrency
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cryptocurrencies.*
import kotlinx.android.synthetic.main.ticker_item.view.*


class CryptocurrenciesActivity : AppCompatActivity() {
    val compositeDisposable = CompositeDisposable()
    private  var cryptos: ArrayList<Cryptocurrency> = ArrayList<Cryptocurrency>()
    private lateinit var cryptoAdapter: CryptoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cryptocurrencies)

        showCryptocurrencies()



    }

    fun showCryptocurrencies(){
        val cryptocurrenciesResponse = getCryptocurrencies().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
        val disposableObserver = cryptocurrenciesResponse.subscribeWith(object : DisposableObserver<List<Cryptocurrency>>(){
            override fun onComplete() {
            }

            override fun onNext(cryptocurrencies: List<Cryptocurrency>) {
                val listSize = cryptocurrencies.size
                Log.e("ITEMS **** ", listSize.toString())
                cryptos.clear()
                cryptos.addAll(cryptocurrencies)
                cryptoAdapter  = CryptoAdapter(cryptos)
                ticker_list.adapter = cryptoAdapter
            }

            override fun onError(e: Throwable) {
                Log.e("ERROR *** ", e.message)
            }
        })

        compositeDisposable.addAll(disposableObserver)
    }

    fun getCryptocurrencies(): Observable<List<Cryptocurrency>>{
        val retrofit = ApiClient.getClient()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        return apiInterface.getCryptocurrencies("0")
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    private inner class CryptoAdapter internal constructor(private val cryptocurrencies: List<Cryptocurrency>): RecyclerView.Adapter<CryptoAdapter.ViewHolder>() {

        override fun onCreateViewHolder(
                parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.ticker_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(
                holder: ViewHolder, position: Int) {
            val name = cryptocurrencies[position].name
            val price = cryptocurrencies[position].price_usd
            holder.nameLabel.text = name
            holder.priceLabel.text = price
        }

        override fun getItemCount(): Int {
            return cryptocurrencies.size
        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var nameLabel = itemView.textview_name
            var priceLabel = itemView.textview_price

            init {

            }
        }

    }
}
