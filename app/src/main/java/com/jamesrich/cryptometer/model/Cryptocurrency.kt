package com.jamesrich.cryptometer.model

/**
 * Created by James on 3/14/2018.
 */
import com.squareup.moshi.Json
import java.io.Serializable
import kotlin.String

data class Cryptocurrency(
        @Json(name="24h_volume_usd") val _24h_volume_usd: String,
        val available_supply: String,
        val id: String,
        val last_updated: String,
        val market_cap_usd: String,
        val name: String,
        val percent_change_1h: String,
        val percent_change_24h: String,
        val percent_change_7d: String,
        val price_btc: String,
        val price_usd: String,
        val rank: String,
        val symbol: String,
        val total_supply: String
):Serializable
