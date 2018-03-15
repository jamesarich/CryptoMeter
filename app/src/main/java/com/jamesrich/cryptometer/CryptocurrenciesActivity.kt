package com.jamesrich.cryptometer

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jamesrich.cryptometer.api.ProjectRepository
import com.jamesrich.cryptometer.model.Cryptocurrency
import com.jamesrich.cryptometer.viewmodel.CryptoTickerViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_cryptocurrencies.*
import kotlinx.android.synthetic.main.ticker_item.view.*


class CryptocurrenciesActivity : AppCompatActivity() {
    val compositeDisposable = CompositeDisposable()
    private var cryptos: ArrayList<Cryptocurrency> = ArrayList<Cryptocurrency>()
    private lateinit var cryptoAdapter: CryptoAdapter

    private lateinit var cryptoTickerViewModel: CryptoTickerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cryptocurrencies)
        cryptoAdapter = CryptoAdapter(cryptos)
        ticker_list.adapter = cryptoAdapter

        cryptoTickerViewModel = ViewModelProviders.of(this).get(CryptoTickerViewModel::class.java)

        cryptoTickerViewModel.getCryptocurrencies().observe(this, Observer { newCryptos ->
            cryptoAdapter.cryptocurrencies.clear()
            newCryptos?.forEach { crypto -> cryptoAdapter.cryptocurrencies.add(crypto) }
            cryptoAdapter.notifyDataSetChanged()
        })

    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    private inner class CryptoAdapter internal constructor(var cryptocurrencies: ArrayList<Cryptocurrency>) : RecyclerView.Adapter<CryptoAdapter.ViewHolder>() {


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
