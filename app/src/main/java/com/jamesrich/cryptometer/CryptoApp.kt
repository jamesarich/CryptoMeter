package com.jamesrich.cryptometer

import android.app.Application
import com.jamesrich.cryptometer.di.cryptoModule
import org.koin.android.ext.android.startKoin

/**
 * Created by James on 3/16/2018.
 */
class CryptoApp: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(cryptoModule))
    }
}