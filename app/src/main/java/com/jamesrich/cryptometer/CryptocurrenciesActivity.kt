package com.jamesrich.cryptometer

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jamesrich.cryptometer.model.Cryptocurrency
import com.jamesrich.cryptometer.viewmodel.CryptoTickerViewModel
import kotlinx.android.synthetic.main.activity_cryptocurrencies.*
import kotlinx.android.synthetic.main.ticker_item.view.*
import org.koin.android.architecture.ext.getViewModel
import org.koin.android.architecture.ext.viewModel


class CryptocurrenciesActivity : AppCompatActivity() {
    private lateinit var cryptoAdapter: CryptoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cryptocurrencies)
        cryptoAdapter = CryptoAdapter(cryptocurrencies = ArrayList<Cryptocurrency>())
        ticker_list.adapter = cryptoAdapter

        getViewModel<CryptoTickerViewModel>().getCryptocurrencies().observe(this, Observer { newCryptos ->
            cryptoAdapter.cryptocurrencies.clear()
            newCryptos?.forEach { crypto -> cryptoAdapter.cryptocurrencies.add(crypto) }
            cryptoAdapter.notifyDataSetChanged()
        })

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
