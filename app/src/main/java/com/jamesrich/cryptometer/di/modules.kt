package com.jamesrich.cryptometer.di

import com.jamesrich.cryptometer.model.CryptoRepo
import com.jamesrich.cryptometer.model.Repo
import com.jamesrich.cryptometer.viewmodel.CryptoTickerViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * Created by James on 3/16/2018.
 */
// Koin module
val cryptoModule : Module = applicationContext {
    viewModel { CryptoTickerViewModel(get()) } // get() will resolve Repository instance
    bean { CryptoRepo() }
}