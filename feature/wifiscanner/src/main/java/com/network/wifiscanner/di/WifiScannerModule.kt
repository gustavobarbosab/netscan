package com.network.wifiscanner.di

import com.network.wifiscanner.presentation.WifiScannerFragment
import com.network.wifiscanner.presentation.WifiScannerViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object WifiScannerModule {
    val instance = module {
        scope<WifiScannerFragment> {
            viewModelOf(::WifiScannerViewModel)
        }
    }
}